package org.itheima.recycler.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.itheima.recycler.R;

/**
 * Created by lyl on 2016/10/3.
 */

public class ItheimaRecyclerView extends RecyclerView {
    /**
     * 0：LinearLayoutManager.VERTICAL(默认)
     * 1：LinearLayoutManager.HORIZONTAL
     * [2,9]：
     */
    private int mSpanCount;

    public ItheimaRecyclerView(Context context) {
        this(context, null);
    }

    public ItheimaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItheimaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.itheima_recyclerView, defStyle, 0);
        if (typedArray != null) {
            mSpanCount = typedArray.getInt(R.styleable.itheima_recyclerView_spanCount, 0);
            typedArray.recycle();
        }
        setSpanCount(mSpanCount);
    }

    public void setSpanCount(int spanCount) {
        mSpanCount = spanCount;
        setLayoutManagerType();
    }

    public int getSpanCount() {
        return mSpanCount;
    }

    private void setLayoutManagerType() {
        LayoutManager layoutManager = null;
        if (getSpanCount() == 0) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else if (getSpanCount() == 1) {//1
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else if (getSpanCount() <= 10) {//[2,10]
            layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        } else if (getSpanCount() > 10) {// >10 横向GridLayout，第二位代表行
            layoutManager = new GridLayoutManager(getContext(), getSpanCount() % 10, GridLayoutManager.HORIZONTAL, false);
        }
        setLayoutManager(layoutManager);

    }
}
