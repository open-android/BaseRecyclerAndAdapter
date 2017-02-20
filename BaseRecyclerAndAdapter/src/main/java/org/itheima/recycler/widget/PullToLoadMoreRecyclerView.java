package org.itheima.recycler.widget;

import android.support.v4.widget.SwipeRefreshLayout;

import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import org.itheima.recycler.R;
import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.bean.BasePageBean;
import org.itheima.recycler.listener.PullToMoreListener;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by lyl on 2016/10/7.
 */

public abstract class PullToLoadMoreRecyclerView<HttpResponseBean extends BasePageBean> extends PullToMoreListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ItheimaRecyclerView mRecyclerView;
    private Class<? extends BaseRecyclerViewHolder> mViewHolderClazz;
    /**
     * 扩展字段
     */
    public Object mMextendObject;

    private LoadingDataListener<HttpResponseBean> mLoadingDataListener;

    public PullToLoadMoreRecyclerView(SwipeRefreshLayout swipeRefreshLayout, ItheimaRecyclerView recyclerView, Class<? extends BaseRecyclerViewHolder> viewHolderClazz) {
        mSwipeRefreshLayout = swipeRefreshLayout;
        mRecyclerView = recyclerView;
        mViewHolderClazz = viewHolderClazz;
        initView();
        initData();
    }

    private void initView() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(getSwipeColorSchemeResources());
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    public void setSpanCount(int spanCount) {
        mRecyclerView.setSpanCount(spanCount);
    }


    protected void initData() {
        mLoadMoreRecyclerViewAdapter = new BaseLoadMoreRecyclerAdapter(mRecyclerView, mViewHolderClazz, getItemResId(), null);
        mLoadMoreRecyclerViewAdapter.setPullAndMoreListener(this);
    }


    public int mCurPage = 1;
    public int mPageSize = 20;

    public int mTotalPage = 0;

    public Call mCall;


    public String mCurPageKey = "curPage";
    public String mPageSizeKey = "pageSize";

    public BaseLoadMoreRecyclerAdapter mLoadMoreRecyclerViewAdapter;

    public abstract int getItemResId();

    public abstract String getApi();

    public int[] getSwipeColorSchemeResources() {
        return new int[]{R.color.colorPrimary};
    }

    Map<String, Object> mParamMap = new HashMap<>();
    Map<String, Object> mHeaderMap = new HashMap<>();

    public Map<String, Object> putParam(String key, Object value) {
        mParamMap.put(key, value);
        return mParamMap;
    }

    public Map<String, Object> putHeader(String key, Object value) {
        mHeaderMap.put(key, value);
        return mHeaderMap;
    }


    @Override
    public void onRefresh() {
        if (mLoadingDataListener != null) {
            mLoadingDataListener.onRefresh();
        }
        requestData();
    }

    @Override
    public void onRefreshLoadMore(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder) {
        if (isMoreData(holder)) {
            holder.loading(null);
            requestData(true);
        } else {
            holder.loadingFinish(null);
            pullLoadFinish();
        }
    }


    public void requestData() {
        mCurPage = 1;
        requestData(false);
    }

    /**
     * 是否有更多数据（可以更具自己的分页条件重写）
     */
    public boolean isMoreData(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder) {
        return mCurPage <= mTotalPage;
    }


    private void requestData(final boolean isLoadMore) {
        if (mLoadingDataListener != null) {
            mLoadingDataListener.onStart();
        }
        mParamMap.put(mCurPageKey, mCurPage);
        mParamMap.put(mPageSizeKey, mPageSize);
        Request request = ItheimaHttp.newGetRequest(getApi());
        request.putParamsMap(mParamMap);

        if (mHeaderMap != null && mHeaderMap.size() > 0) {
            request.putHeaderMap(mHeaderMap);
        }
        mCall = ItheimaHttp.send(request, new HttpResponseListener<HttpResponseBean>() {
            @Override
            public void onResponse(HttpResponseBean responseBean, Headers headers) {
                mTotalPage = responseBean.getTotalPage();
                mCurPage++;
                mLoadMoreRecyclerViewAdapter.addDatas(isLoadMore, responseBean.getItemDatas());
                pullLoadFinish();
               /* if (mHttpResponseCall != null) {
                    mHttpResponseCall.onResponse(responseBean);
                }*/

                if (mLoadingDataListener != null) {
                    mLoadingDataListener.onSuccess(responseBean, headers);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                super.onFailure(call, e);
                pullLoadFinish();
                /*if (mHttpResponseCall != null) {
                    mHttpResponseCall.onFailure(call, e);
                }*/
                if (mLoadingDataListener != null) {
                    mLoadingDataListener.onFailure();
                }

            }


            @Override
            public Class getClazz() {
                return PullToLoadMoreRecyclerView.this.getClass();
            }
        });

    }

    //private HttpResponseListener<HttpResponseBean> mHttpResponseCall;

    /*public void setHttpResponseListener(HttpResponseListener<HttpResponseBean> call) {
        mHttpResponseCall = call;
    }*/

    public void pullLoadFinish() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void free() {
        mRecyclerView = null;
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout = null;

        }
        if (mCall != null) {
            mCall.cancel();
            mCall = null;
        }

        if (mLoadMoreRecyclerViewAdapter != null) {
            mLoadMoreRecyclerViewAdapter.setPullAndMoreListener(null);
            mLoadMoreRecyclerViewAdapter = null;
        }

        //mHttpResponseCall = null;
        mLoadingDataListener = null;
    }
    /*|||||||||||||||||||||||||||||||||||||||||||||||||||||*/

    public void setCurPage(int curPage) {
        mCurPage = curPage;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        mTotalPage = totalPage;
    }

    public void setCurPageKey(String curPageKey) {
        mCurPageKey = curPageKey;
    }

    public void setPageSizeKey(String pageSizeKey) {
        mPageSizeKey = pageSizeKey;
    }

    /*///////////////////////////////////////////////////*/
    public void setLoadingDataListener(LoadingDataListener<HttpResponseBean> loadingDataListener) {
        mLoadingDataListener = loadingDataListener;
    }

    /**
     * 监听下啦刷新 & 上啦加载更多
     */
    public static abstract class LoadingDataListener<T> {
        /**
         * 下啦刷新回调
         */
        public void onRefresh() {

        }

        /**
         * http请求开始
         */
        public void onStart() {
        }

        /**
         * http请求数据成功
         *
         * @param t
         */
        public void onSuccess(T t, Headers headers) {
        }

        public void onFailure() {

        }
    }
}
