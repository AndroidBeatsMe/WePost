package com.nancyberry.wepost.ui.timeline;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nancyberry.wepost.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nan.zhang on 4/12/16.
 */
public abstract class SwipeRefreshListActivity extends Activity
        implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    public enum RefreshMode {
        RESET,
        LOAD_MORE,
        REFRESH
    }

    public static final String TAG = SwipeRefreshListActivity.class.getSimpleName();

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.listview)
    ListView listView;

    protected View footerView;

    protected boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        ButterKnife.bind(this);

        footerView = View.inflate(this, R.layout.footer_listview, null);
        listView.addFooterView(footerView);

        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnScrollListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public abstract void requestData(RefreshMode mode);

    public void onPullUpToLoadMore() {
        Log.d(TAG, "onPullUpToLoadMore");
        listView.addFooterView(footerView);
        requestData(RefreshMode.LOAD_MORE);
    }

    public void onPullDownToRefresh() {
        requestData(RefreshMode.REFRESH);
    }

    public void onLoadMoreComplete() {
//        isLoading = false;
//        listView.removeFooterView(footerView);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (scrollState == SCROLL_STATE_FLING) {
//
//        } else if (scrollState == SCROLL_STATE_IDLE) {
//            if (canFooterAutoLoadMore() && !isRefreshing()) {
//                for (int i = 0; i < listView.getFooterViewsCount(); i++) {
//                    if (listView.getChildAt(listView.getChildCount() - i - 1) == footerView) {
////                        if (getRefreshConfig().canLoadMore) {
////                            final View layLoading = footerView.findViewById(R.id.layLoading);
////                            final TextView btnLoadMore = (TextView) footerView.findViewById(R.id.btnLoadMore);
////                            layLoading.setVisibility(View.VISIBLE);
////                            btnLoadMore.setVisibility(View.GONE);
////
////                            onPullUpToLoadMore();
////                        }
//
//                        onPullUpToLoadMore();
//
//                        break;
//                    }
//                }
//            }
//        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > listView.getFooterViewsCount() && firstVisibleItem + visibleItemCount == totalItemCount - listView.getFooterViewsCount()) {
            if (!isLoading) {
                isLoading = true;
                onPullUpToLoadMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        onPullDownToRefresh();
    }

    public boolean isRefreshing() {
        return swipeRefreshLayout.isRefreshing();
    }

    public boolean canFooterAutoLoadMore() {
        return true;
    }
}
