package com.nancyberry.wepost.ui.timeline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.LinkMovementMethod;
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
import com.nancyberry.wepost.sina.request.params.GetFriendsTimelineReqParams;
import com.nancyberry.wepost.support.model.Account;
import com.nancyberry.wepost.support.model.StatusContent;
import com.nancyberry.wepost.support.model.StatusContentList;
import com.nancyberry.wepost.ui.widget.NineGridLayout;
import com.nancyberry.wepost.ui.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nan.zhang on 4/5/16.
 */
public class FriendTimelineActivity extends Activity implements RefreshLayout.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = FriendTimelineActivity.class.getSimpleName();
    public static final String BUNDLE_ACCOUNT = "account";
    private Subscription mSubscription;
    private List<StatusContent> statusContentList;
    private FriendTimelineAdapter adapter;
    private static final int statusesCountPerPage = 20;
    private int pagesCount = 0;
    private String token;

    @Bind(R.id.layout_refresh)
    RefreshLayout refreshLayout;

    @Bind(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_timeline);
        ButterKnife.bind(this);

        Account account = (Account) getIntent().getSerializableExtra(BUNDLE_ACCOUNT);
        token = account.getAccessToken().getValue();
        statusContentList = new ArrayList<>();
        adapter = new FriendTimelineAdapter(statusContentList);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(true);

        refreshLayout.setListView(listView);
        refreshLayout.setFooterView(this, R.layout.footer_listview);
        refreshLayout.bindOnScrollListener(null, true);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    public static void actionStart(Context context, Account account) {
        Intent intent = new Intent(context, FriendTimelineActivity.class);
        intent.putExtra(BUNDLE_ACCOUNT, account);
        context.startActivity(intent);
    }

    public class FriendTimelineAdapter extends ArrayAdapter<StatusContent> {
        public FriendTimelineAdapter(List<StatusContent> statusContentList) {
            super(FriendTimelineActivity.this, 0, statusContentList);
        }

        @Override
        public StatusContent getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final StatusContent statusContent = getItem(position);
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = FriendTimelineActivity.this.getLayoutInflater().inflate(R.layout.item_status, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.name.setText(statusContent.getUser().getScreenName());

            Glide.with(FriendTimelineActivity.this)
                    .load(statusContent.getUser().getProfileImageUrl())
                    .centerCrop()
//                    .placeholder(R.color.comm_gray)
                    .crossFade()
                    .into(viewHolder.avatar);

            // desc
            viewHolder.desc.setText(StringUtils.getDesc(statusContent.getCreatedAt(), statusContent.getSource()));

            // content
            viewHolder.content.setText(StatusContent.parseText(FriendTimelineActivity.this, statusContent.getText()));
            viewHolder.content.setMovementMethod(LinkMovementMethod.getInstance());

            // counts
            viewHolder.attitudesCount.setText(String.valueOf(statusContent.getAttitudesCount()));
            viewHolder.repostsCount.setText(String.valueOf(statusContent.getRepostsCount()));
            viewHolder.commentsCount.setText(String.valueOf(statusContent.getCommentsCount()));

            StatusContent picStatusContent = statusContent;

            // repost
            if (statusContent.getRetweetedStatus() == null) {
                viewHolder.repostLayout.setVisibility(View.GONE);
            } else {
                StatusContent repostStatusContent = statusContent.getRetweetedStatus();
                viewHolder.repostLayout.setVisibility(View.VISIBLE);
                StringBuilder repostText = new StringBuilder();

                if (repostStatusContent.getUser() != null) {
                    repostText
                            .append("@")
                            .append(repostStatusContent.getUser().getScreenName())
                            .append(":");
                }

                repostText.append(repostStatusContent.getText());
                viewHolder.repostContent.setText(StatusContent.parseText(FriendTimelineActivity.this, repostText.toString()));
                viewHolder.repostContent.setMovementMethod(LinkMovementMethod.getInstance());

                picStatusContent = repostStatusContent;
            }

            // pictures
            if (picStatusContent.getPicUrls().isEmpty()) {
                viewHolder.pics.setVisibility(View.GONE);
            } else {
                viewHolder.pics.setVisibility(View.VISIBLE);
                viewHolder.pics.setImageData(picStatusContent);
            }

            view.setClickable(true);
            view.setFocusable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf(TAG, "onItemClick, position: " + position);
                    TimelineCommentsActivity.actionStart(FriendTimelineActivity.this, token, statusContent);
                }
            });

            return view;
        }

        class ViewHolder {
            @Bind(R.id.img_avatar)
            ImageView avatar;
            @Bind(R.id.text_name)
            TextView name;
            @Bind(R.id.text_desc)
            TextView desc;
            @Bind(R.id.text_content)
            TextView content;
            @Bind(R.id.text_attitudes_count)
            TextView attitudesCount;
            @Bind(R.id.text_reposts_count)
            TextView repostsCount;
            @Bind(R.id.text_comments_count)
            TextView commentsCount;
            @Bind(R.id.img_pics)
            NineGridLayout pics;
            @Bind(R.id.layout_repost)
            RelativeLayout repostLayout;
            @Bind(R.id.repost_divider)
            View repostDivider;
            @Bind(R.id.txt_repost_content)
            TextView repostContent;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onRefresh() {
        pagesCount = 0;
        // Add footView in case it has been removed
        refreshLayout.setFooterView(this, R.layout.footer_listview);

        GetFriendsTimelineReqParams params = new GetFriendsTimelineReqParams()
                .withAccessToken(token)
                .withPage(++pagesCount);

        Map<String, String> queryMap = HttpUtils.pojoToMap(params);

        mSubscription = Http.getSinaApi()
                .getFriendsTimeline(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusContentList>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        // don't forget this
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(StatusContentList list) {
                        Log.d(TAG, "onNext");
                        statusContentList.clear();
                        statusContentList.addAll(list.getValue());
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onLoadMore() {

        GetFriendsTimelineReqParams params = new GetFriendsTimelineReqParams()
                .withAccessToken(token)
                .withPage(++pagesCount);

        Map<String, String> queryMap = HttpUtils.pojoToMap(params);

        mSubscription = Http.getSinaApi()
                .getFriendsTimeline(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusContentList>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        refreshLayout.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        refreshLayout.setLoading(false);
                    }

                    @Override
                    public void onNext(StatusContentList list) {
                        Log.d(TAG, "onNext");
                        statusContentList.addAll(list.getValue());
                        adapter.notifyDataSetChanged();

                        refreshLayout.hideFooterView();
                        // scroll to the next item
                        Log.d(TAG, "scroll to " + statusesCountPerPage * (pagesCount - 1));
                        listView.smoothScrollToPosition(statusesCountPerPage * (pagesCount - 1));

                        refreshLayout.setLoading(false);
                    }
                });
    }
}
