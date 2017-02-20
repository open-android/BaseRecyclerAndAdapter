package org.itheima.recycler.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.google.gson.annotations.SerializedName;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.RequestMethod;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import org.itheima.recycler.L;
import org.itheima.recycler.R;
import org.itheima.recycler.adapter.BaseRecyclerAdapter;
import org.itheima.recycler.bean.BasePageBean;
import org.itheima.recycler.bean.SwipeRefreshBean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by lyl on 2016/11/20.
 */

public class SwipeRefreshHeaderRecyclerView extends BaseSwipeRefreshHeaderRecyclerView implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshBean mSwipeRefreshBean;

    public int mCurPage = 1;
    public int mPageSize = 20;
    public int mTotalPage = 0;
    public String mCurPageKey = "currentPage";
    public String mPageSizeKey = "pageSize";
    public String mTotalPageKey = "totalPage";

    private RequestMethod mRequestMethod;

    public Call mCall;

    private BaseRecyclerAdapter mBaseRecyclerAdapter;

    private SwipeLoadingDataListener mSwipeLoadingDataListener;


    public SwipeRefreshHeaderRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRefreshHeaderRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.colorPrimary);
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (mSwipeLoadingDataListener != null) {
            mSwipeLoadingDataListener.onRefresh();
        }
        mCurPage = 1;
        mParamMap.put(mCurPageKey, mCurPage);
        requestData(false, mRequestMethod, mParamMap);
    }

    private Map<String, Object> mParamMap = new HashMap<String, Object>();

    public void requestData(RequestMethod method, Map<String, Object> paramMap) {
        requestData(false, method, paramMap);

    }

    public void requestData(final boolean isLoadMore, RequestMethod method, Map<String, Object> paramMap) {
        if (mSwipeLoadingDataListener != null) {
            mSwipeLoadingDataListener.onStart();
        }
        mRequestMethod = method;
        if (paramMap != null) {
            mParamMap.putAll(paramMap);
        }
        Request request = null;
        if (RequestMethod.GET.equals(method)) {
            request = ItheimaHttp.newGetRequest(mSwipeRefreshBean.apiUrl);
        } else {
            request = ItheimaHttp.newPostRequest(mSwipeRefreshBean.apiUrl);
        }
        request.putParamsMap(mParamMap);
        mCall = ItheimaHttp.send(request, new HttpResponseListener<BasePageBean>() {
            @Override
            public void onResponse(BasePageBean responseBean, Headers headers) {
                if (mSwipeLoadingDataListener != null) {
                    mSwipeLoadingDataListener.onSuccess(responseBean, headers);
                }
                mTotalPage = responseBean.totalPage;
                mCurPage++;
                mBaseRecyclerAdapter.addDatas(isLoadMore, responseBean.getItemDatas());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                super.onFailure(call, e);

                if (mSwipeLoadingDataListener != null) {
                    mSwipeLoadingDataListener.onFailure();
                }

            }


            @Override
            public Class getClazz() {
                return mSwipeRefreshBean.httpResponseBeanClazz;
            }
        });


    }


   /* public void prepareData(SwipeRefreshBean swipeRefreshBean) {
        mSwipeRefreshBean = swipeRefreshBean;
        try {
            //反射获取有关于分页数据的值
            Field pageSizeField = mSwipeRefreshBean.httpResponseBeanClazz.getField("pageSize");
            Field currentPageField = mSwipeRefreshBean.httpResponseBeanClazz.getField("currentPage");
            Field totalPageField = mSwipeRefreshBean.httpResponseBeanClazz.getField("totalPage");

            mPageSize = pageSizeField.getInt(20);
            mCurPage = currentPageField.getInt(1);
            //mTotalPage = totalPageField.getInt(0);

            SerializedName pageSizeAnnotation = pageSizeField.getAnnotation(SerializedName.class);
            SerializedName currentPageAnnotation = currentPageField.getAnnotation(SerializedName.class);
            SerializedName totalPageAnnotation = totalPageField.getAnnotation(SerializedName.class);
            if (pageSizeAnnotation != null) {
                mPageSizeKey = pageSizeAnnotation.value();
            }
            if (currentPageAnnotation != null) {
                mCurPageKey = currentPageAnnotation.value();
            }
            if (totalPageAnnotation != null) {
                mTotalPageKey = currentPageAnnotation.value();
            }

        } catch (Exception e) {
            L.e(e);
        }

    }*/


    public void setAdapter(BaseRecyclerAdapter adapter) {
        mBaseRecyclerAdapter = adapter;
        mItheimaRecyclerView.setAdapter(adapter);
    }

    public <T> void setSwipeLoadingDataListener(SwipeLoadingDataListener<T> swipeLoadingDataListener) {
        mSwipeLoadingDataListener = swipeLoadingDataListener;
    }

    /**
     * 监听下啦刷新 & 上啦加载更多
     *
     * @param <T>
     */
    public static abstract class SwipeLoadingDataListener<T> {
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
