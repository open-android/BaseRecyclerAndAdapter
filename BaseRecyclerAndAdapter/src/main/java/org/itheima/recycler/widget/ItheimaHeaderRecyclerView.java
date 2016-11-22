package org.itheima.recycler.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.itheima.recycler.R;
import org.itheima.recycler.adapter.BaseRecyclerAdapter;

/**
 * Created by lyl on 2016/11/20.
 */

public class ItheimaHeaderRecyclerView extends FrameLayout {

    private RecyclerViewHeader mRecyclerViewHeader;
    private ItheimaRecyclerView mItheimaRecyclerView;
    private int mSpanCount;

    private View mHeaderView;

    public ItheimaHeaderRecyclerView(@NonNull Context context) {
        this(context, null, 0);
    }

    public ItheimaHeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItheimaHeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
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
        post(new Runnable() {
            @Override
            public void run() {
                mHeaderView = getChildAt(0);
                removeView(mHeaderView);

                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.itheima_header_recyclerview_layout, ItheimaHeaderRecyclerView.this, false);
                mRecyclerViewHeader = (RecyclerViewHeader) contentView.findViewById(R.id.itheima_recycerview_header);
                mItheimaRecyclerView = (ItheimaRecyclerView) contentView.findViewById(R.id.itheima_recyclerview);
                mRecyclerViewHeader.addView(mHeaderView);
                addView(contentView);

                setSpanCount(mSpanCount);
                mRecyclerViewHeader.attachTo(mItheimaRecyclerView);
            }
        });

    }

    public void setSpanCount(int spanCount) {
        mItheimaRecyclerView.setSpanCount(spanCount);
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        mItheimaRecyclerView.setAdapter(adapter);

    }

    public ItheimaRecyclerView getRecycerView() {
        return mItheimaRecyclerView;
    }
}
