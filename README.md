
#RecyclerView.Adapter封装

开始
===
在project的build.gradle添加如下代码(如下图)
```
allprojects {
    repositories {
        ...
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
![image](jitpack.png)
在build.gradle添加依赖
```
compile 'com.github.itcastsh:BaseRecyclerAndAdapter:0.1.1'
compile 'com.jakewharton:butterknife:8.4.0'
apt 'com.jakewharton:butterknife-compiler:8.4.0'
```

####BaseRecyclerAdapter使用方式
```
adapter = new BaseRecyclerAdapter(recyclerView
        , ViewHolder.class
        , item_recyclerview_resid
        , datas);
        
@param recyclerView
@param viewHolderClazz
@param itemResId       recyclerView条目的资源id
@param datas           recyclerView展示的数据集合（可以传null）
```


####ViewHolder模板（ViewHolder如果是内部类必须加上static和public关键字）
```
public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<DataBean> {
    //换成你布局文件中的id
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
        super(parentView, itemResId);
    }

    /**
     * 绑定数据的方法，在mData获取数据（mData声明在基类中）
     */
    @Override
    public void onBindRealData() {
        tvTitle.setText(mData.title);
    }


    /**
     * 给按钮添加点击事件（button改成你要添加点击事件的id）
     * @param v
     */
    @OnClick(R.id.button)
    public void click(CheckBox v) {
    }
}
```


####向Adapter中添加数据
```
@param isLoadMore 数据是否累加
@param datas      List数据

adapter.addDatas(true,datas);
```
