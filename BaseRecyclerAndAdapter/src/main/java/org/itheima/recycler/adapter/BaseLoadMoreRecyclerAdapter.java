package org.itheima.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.itheima.recycler.R;
import org.itheima.recycler.listener.PullToMoreListener;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by lyl on 2016/10/7.
 */

public class BaseLoadMoreRecyclerAdapter extends BaseRecyclerAdapter {
    //加载更多类型
    protected final int ITHEIMA_FOOT_TYPE = "ITHEIMA_FOOT_TYPE".hashCode();

    protected PullToMoreListener mPullAndMoreListener;


    public BaseLoadMoreRecyclerAdapter(RecyclerView recyclerView, Class<? extends BaseRecyclerViewHolder> viewHolderClazz, int itemResId, List datas) {
        super(recyclerView, viewHolderClazz, itemResId, datas);

    }

    @Override
    public int getItemViewType(int position) {
        return (getItemCount() - 1 == position) ? ITHEIMA_FOOT_TYPE : super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        return itemCount == 0 ? 0 : itemCount + 1;
    }

    @Override
    public final BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITHEIMA_FOOT_TYPE) {
            return new LoadMoreViewHolder(parent, R.layout.itheima_item_loadmore_layout);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (position == mItemCount) {
            holder.onBindData(position, null);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void setPullAndMoreListener(PullToMoreListener listener) {
        mPullAndMoreListener = listener;
    }


    public class LoadMoreViewHolder extends BaseRecyclerViewHolder {

        ProgressBar mProgressBar;

        TextView tvLoading;

        public LoadMoreViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
        }

        @Override
        protected void onBindRealData() {
            if (mPullAndMoreListener != null) {
                mPullAndMoreListener.onRefreshLoadMore(this);
            }
        }

        public void loading(String txt) {
            mProgressBar.setVisibility(View.VISIBLE);
            tvLoading.setText(TextUtils.isEmpty(txt) ? "加载中..." : txt);
        }

        public void loadingFinish(String txt) {
            mProgressBar.setVisibility(View.GONE);
            tvLoading.setText(TextUtils.isEmpty(txt) ? "没有更多数据" : txt);
        }
    }
}
