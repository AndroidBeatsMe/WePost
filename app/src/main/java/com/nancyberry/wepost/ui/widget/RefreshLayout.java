package com.nancyberry.wepost.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
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
        footerView = LayoutInflater.from(context).inflate(resourceId, null);
        listView.addFooterView(footerView);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > listView.getFooterViewsCount()
                        && firstVisibleItem + visibleItemCount == totalItemCount - listView.getFooterViewsCount()) {
                    if (!loading) {
                        loading = true;

                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            }
        });
    }

    public void removeFooterView() {
        listView.removeFooterView(footerView);
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
}
