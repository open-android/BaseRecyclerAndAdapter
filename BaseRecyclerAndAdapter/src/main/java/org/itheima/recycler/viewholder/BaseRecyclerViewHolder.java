package org.itheima.recycler.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by lyl on 2016/10/3.
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected int mPosition;
    protected T mData;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext().getApplicationContext();
    }

    public BaseRecyclerViewHolder(ViewGroup parentView, int itemResId) {
        this(LayoutInflater.from(parentView.getContext()).inflate(itemResId, parentView, false));
    }

    public final void onBindData(int position, T t) {
        this.mPosition = position;
        this.mData = t;
        onBindRealData();
    }

    protected abstract void onBindRealData();

}
