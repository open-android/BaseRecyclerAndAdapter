package org.itheima.recycler.bean;

import java.util.List;

/**
 * 分页的基类bean
 * Created by lyl on 2016/10/7.
 */

public abstract class BasePageBean<ItemBean> {
    //@SerializedName("totalCount")
    public int totalCount = 0;
    public int currentPage = 1;
    public int totalPage = 0;
    public int pageSize = 20;

    public abstract List<ItemBean> getItemDatas();

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}
