package org.itheima.recycler.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.itheima.recycler.R;

/**
 * Created by lyl on 2016/11/20.
 */

public class BaseSwipeRefreshHeaderRecyclerView extends SwipeRefreshLayout {
    protected View mHeaderView;
    protected int mSpanCount;
    protected RecyclerViewHeader mRecyclerViewHeader;
    protected ItheimaRecyclerView mItheimaRecyclerView;

    public BaseSwipeRefreshHeaderRecyclerView(Context context) {
        this(context, null);
    }

    public BaseSwipeRefreshHeaderRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
        initView();

    }

    private void initAttr(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.itheima_recyclerView, defStyleAttr, 0);
        if (typedArray != null) {
            mSpanCount = typedArray.getInt(R.styleable.itheima_recyclerView_spanCount, 0);
            typedArray.recycle();
        }

    }

    private void initView() {
        mHeaderView = getChildAt(0);
        removeView(mHeaderView);
        addView(LayoutInflater.from(getContext()).inflate(R.layout.itheima_header_recyclerview_layout, this, false));
        mRecyclerViewHeader = (RecyclerViewHeader) findViewById(R.id.itheima_recycerview_header);
        mItheimaRecyclerView = (ItheimaRecyclerView) findViewById(R.id.itheima_recyclerview);
        mRecyclerViewHeader.addView(mHeaderView);

        setSpanCount(mSpanCount);
        mRecyclerViewHeader.attachTo(mItheimaRecyclerView);
    }


    public void setSpanCount(int spanCount) {
        mItheimaRecyclerView.setSpanCount(spanCount);
    }
}
