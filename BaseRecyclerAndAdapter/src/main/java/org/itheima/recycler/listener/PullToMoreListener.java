package org.itheima.recycler.listener;

import android.support.v4.widget.SwipeRefreshLayout;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by lyl on 2016/10/7.
 */

public interface PullToMoreListener extends SwipeRefreshLayout.OnRefreshListener {
    /**
     * 加载更多
     */
    void onRefreshLoadMore(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder);
}
