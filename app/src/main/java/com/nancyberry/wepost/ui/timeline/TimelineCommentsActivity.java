package com.nancyberry.wepost.ui.timeline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nancyberry.wepost.R;
import com.nancyberry.wepost.common.util.HttpUtils;
import com.nancyberry.wepost.common.util.StringUtils;
import com.nancyberry.wepost.sina.Http;
import com.nancyberry.wepost.sina.request.params.GetTimelineCommentsReqParams;
import com.nancyberry.wepost.support.model.StatusComment;
import com.nancyberry.wepost.support.model.StatusCommentList;
import com.nancyberry.wepost.support.model.StatusContent;
import com.nancyberry.wepost.ui.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nan.zhang on 5/16/16.
 */
public class TimelineCommentsActivity extends Activity implements RefreshLayout.OnLoadMoreListener, RefreshLayout.OnRefreshListener {

    public static final String TAG = TimelineCommentsActivity.class.getSimpleName();
    public static final String BUNDLE_STATUS_ID = TimelineCommentsActivity.class.getName() + ".BUNDLE_STATUS_ID";
    public static final String BUNDLE_TOKEN = TimelineCommentsActivity.class.getName() + ".BUNDLE_TOKEN";
    private static final int commentsCountPerPage = 50;
    private int pagesCount = 0;
    private long commentsTotalCount = 0;

    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.layout_refresh)
    RefreshLayout refreshLayout;

    private Subscription subscription;
    private List<StatusComment> statusCommentList;
    private TimelineCommentsAdapter adapter;

    private String token;
    private Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_comment);
        ButterKnife.bind(this);

        token = getIntent().getStringExtra(BUNDLE_TOKEN);
        id = getIntent().getLongExtra(BUNDLE_STATUS_ID, -1);

        statusCommentList = new ArrayList<>();
        adapter = new TimelineCommentsAdapter(statusCommentList);
        listView.setAdapter(adapter);

        refreshLayout.setListView(listView);
        refreshLayout.setFooterView(this, R.layout.footer_listview);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);

        onLoadMore();
    }

    public static void actionStart(Context context, String token, Long id) {
        Intent intent = new Intent(context, TimelineCommentsActivity.class);
        intent.putExtra(BUNDLE_STATUS_ID, id);
        intent.putExtra(BUNDLE_TOKEN, token);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        pagesCount = 0;

        GetTimelineCommentsReqParams params = new GetTimelineCommentsReqParams()
                .withAccessToken(token)
                .withId(id)
                .withPage(++pagesCount);

        subscription = Http.getSinaApi()
                .getTimelineComments(HttpUtils.pojoToMap(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusCommentList>() {
                    @Override
                    public void onCompleted() {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(StatusCommentList list) {
                        statusCommentList.clear();
                        statusCommentList.addAll(list.getValue());
                        commentsTotalCount = list.getTotalNumber();
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onLoadMore() {
        GetTimelineCommentsReqParams params = new GetTimelineCommentsReqParams()
                .withAccessToken(token)
                .withId(id)
                .withPage(++pagesCount);

        subscription = Http.getSinaApi().getTimelineComments(HttpUtils.pojoToMap(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusCommentList>() {
                    @Override
                    public void onCompleted() {
                        refreshLayout.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        refreshLayout.setLoading(false);
                    }

                    @Override
                    public void onNext(StatusCommentList list) {
                        Log.d(TAG, "" + list.getValue().size());
                        statusCommentList.addAll(list.getValue());
                        adapter.notifyDataSetChanged();
                        // scroll to the next item
                        Log.d(TAG, "scroll to " + commentsCountPerPage * (pagesCount - 1));
                        listView.smoothScrollToPosition(commentsCountPerPage * (pagesCount - 1));

                        refreshLayout.setLoading(false);
                    }
                });
    }

    public class TimelineCommentsAdapter extends ArrayAdapter<StatusComment> {
        public TimelineCommentsAdapter(List<StatusComment> statusCommentList) {
            super(TimelineCommentsActivity.this, 0, statusCommentList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StatusComment statusComment = getItem(position);
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = TimelineCommentsActivity.this.getLayoutInflater().inflate(R.layout.item_comment, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.name.setText(statusComment.getUser().getScreenName());
            Glide.with(TimelineCommentsActivity.this)
                    .load(statusComment.getUser().getProfileImageUrl())
                    .centerCrop()
                    .crossFade()
                    .into(viewHolder.avatar);
            viewHolder.content.setText(StatusContent.parseText(view.getContext(), statusComment.getText()));
            viewHolder.desc.setText(StringUtils.getDesc(statusComment.getCreatedAt(), statusComment.getSource()));

            // repost
            viewHolder.repostLayout.setVisibility(View.GONE);
            return view;
        }

        class ViewHolder {
            @Bind(R.id.imgPhoto)
            ImageView avatar;
            @Bind(R.id.txtName)
            TextView name;
            @Bind(R.id.imgRePhoto)
            ImageView repostAvatar;
            @Bind(R.id.txtReContent)
            TextView repostContent;
            @Bind(R.id.layRe)
            RelativeLayout repostLayout;
            @Bind(R.id.txtContent)
            TextView content;
            @Bind(R.id.txtDesc)
            TextView desc;
            @Bind(R.id.btnMenus)
            ImageView button;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
