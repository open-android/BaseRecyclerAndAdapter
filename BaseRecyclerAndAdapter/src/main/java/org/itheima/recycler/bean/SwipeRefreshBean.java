package org.itheima.recycler.bean;

import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;

/**
 * Created by lyl on 2016/11/21.
 */

public class SwipeRefreshBean {

    public int recyclerViewItemResId;

    public Class<? extends BaseRecyclerViewHolder> viewHolderClazz;


    public Class<? extends BasePageBean> httpResponseBeanClazz;

    public String apiUrl;


}
