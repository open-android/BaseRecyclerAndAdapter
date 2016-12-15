package org.itheima.recycler.listener;

import android.support.v4.widget.SwipeRefreshLayout;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by lyl on 2016/10/7.
 */

public abstract class PullToMoreListener implements SwipeRefreshLayout.OnRefreshListener {
    /**
     * 加载更多
     */
    public abstract void onRefreshLoadMore(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder);

    public void onRefresh() {
    }
}
