package com.nancyberry.wepost.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by nan.zhang on 4/12/16.
 */
public class RefreshLayout extends SwipeRefreshLayout {

    public static final String TAG = RefreshLayout.class.getSimpleName();

    ListView listView;

    protected View footerView;

    protected boolean loading;

    private OnLoadMoreListener onLoadMoreListener;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setFooterView(Context context, int resourceId) {
        if (footerView != null) {
            return;
        }

        footerView = LayoutInflater.from(context).inflate(resourceId, null);
        listView.addFooterView(footerView);
        hideFooterView();
    }

    public void bindOnScrollListener(AbsListView.OnScrollListener onScrollListener, boolean useDefault) {
        if (useDefault) {
            listView.setOnScrollListener(new DefaultOnScrollListener());
        } else {
            listView.setOnScrollListener(onScrollListener);
        }
    }

    public void removeFooterView() {
        Log.d(TAG, "remove footerView");
        listView.removeFooterView(footerView);
        footerView = null;
    }

    public void hideFooterView() {
        footerView.setVisibility(GONE);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        /**
         * Implement load more logic, don't forget to set loading to false when the process is done.
         */
        void onLoadMore();
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }

    public class DefaultOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                Log.d(TAG, "onScroll to the end! Should load more");
                if (footerView == null) {
                    Log.d(TAG, "no footerView, stop loading more");
                    return;
                }

                if (!loading) {
                    Log.d(TAG, "Show footerView");
                    footerView.setVisibility(VISIBLE);

                    loading = true;

                    if (onLoadMoreListener != null) {
                        Log.d(TAG, "loading more...");
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        }
    }
}
