package org.itheima.recycler.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mwqi on 2017/1/19.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    //内部接口，定义点击方法以及长按方法
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() { //这里选择SimpleOnGestureListener实现类，可以根据需要选择重写的方法
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && mListener != null) {
                            mListener.onItemClick(childView, recyclerView.getChildLayoutPosition(childView));
                            return true;
                        }
                        return false;
                    }

                    //长按事件
                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && mListener != null) {
                            mListener.onItemLongClick(childView, recyclerView.getChildLayoutPosition(childView));
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector != null && mGestureDetector.onTouchEvent(e)) {
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
