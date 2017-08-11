
#RecyclerView & RecyclerView.Adapter封装

开源项目:[https://github.com/open-android/BaseRecyclerAndAdapter](https://github.com/open-android/BaseRecyclerAndAdapter "地址")

* 爱生活,爱学习,更爱做代码的搬运工,分类查找更方便请下载黑马助手app


![黑马助手.png](http://upload-images.jianshu.io/upload_images/4037105-f777f1214328dcc4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 使用步骤
### 1. 在project的build.gradle添加如下代码(如下图)
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
![](http://oi5nqn6ce.bkt.clouddn.com/itheima/booster/code/jitpack.png)

### 2. 在Module的build.gradle添加依赖
```groovy
compile 'com.github.open-android:BaseRecyclerAndAdapter:0.5.13'
compile 'com.jakewharton:butterknife:8.4.0'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
```

### 3. 添加权限
```groovy
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
### ItheimaRecyclerView使用方式


* 网格RecyclerView(app:spanCount="2"，spanCount取值范围[1,10])

```xml
<org.itheima.recycler.widget.ItheimaRecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:spanCount="2"/>
```
* 垂直滚动RecyclerView
```xml
<org.itheima.recycler.widget.ItheimaRecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
* 横向滚动RecyclerView（11:一行，12:二行.......,spanCount取值范围[11,19]）
```xml
<org.itheima.recycler.widget.ItheimaRecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:spanCount="12"/>
```

### ItheimaRecyclerView添加头

![](http://upload-images.jianshu.io/upload_images/4037105-63a59d3c9fe90c08.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* xml代码
```xml
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <org.itheima.recycler.widget.ItheimaRecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <org.itheima.recycler.header.RecyclerViewHeader
        android:id="@+id/recycler_header"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal|top">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:text="header"/>
    </org.itheima.recycler.header.RecyclerViewHeader>
</FrameLayout>
```

* java代码

```java
RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
header.attachTo(recyclerView);
```

### 给RecyclerView的item添加点击事件和长按事件

```java
ItemClickSupport itemClickSupport = new ItemClickSupport(mRecyclerView);
//点击事件
itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Toast.makeText(recyclerView.getContext(), "我被点击了", Toast.LENGTH_SHORT).show();
    }
});
//长按事件
itemClickSupport.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        Toast.makeText(recyclerView.getContext(), "我被长按了", Toast.LENGTH_SHORT).show();
        return false;
    }
});
```

### BaseRecyclerAdapter使用方式

```java
adapter = new BaseRecyclerAdapter(recyclerView
        , MyRecyclerViewHolder.class
        , R.layout.item_reyclerview
        , datas);

//@param recyclerView
//@param viewHolderClazz
//@param itemResId       recyclerView条目的资源id
//@param datas           recyclerView展示的数据集合（可以传null）
```

### ViewHolder模板（ViewHolder如果是内部类必须加上static和public关键字）

```java
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
    public void click(View v) {
    }
}
```

### 下啦刷新 & 加载更多组合控件（下啦刷新和加载更多内部实现，你只需要在对应的ViewHolder做数据绑定即可）
* http请求初始化
```java
ItheimaHttp.init(context, baseUrl);//使用前必须调用（如果调用过则不需要重复调用）
```
* xml布局
```xml
<android.support.v4.widget.SwipeRefreshLayout
    android:id="@+id/swipe_refresh_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <org.itheima.recycler.widget.ItheimaRecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</android.support.v4.widget.SwipeRefreshLayout>
```
* java代码实现布局
```java
pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<Bean>(mSwipeRefreshLayout, mRecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.item_recylerview;
            }

            @Override
            public String getApi() {
                //接口
                return "order/list";
            }
             //是否加载更多的数据，根据业务逻辑自行判断，true表示有更多的数据，false表示没有更多的数据，如果不需要监听可以不重写该方法
             @Override
            public boolean isMoreData(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder) {
                System.out.println("isMoreData" + holder);

                return true;
            }
        };
```

* 设置监听
```java
pullToLoadMoreRecyclerView.setLoadingDataListener(new PullToLoadMoreRecyclerView.LoadingDataListener<Bean>() {

        @Override
        public void onRefresh() {
            //监听下啦刷新，如果不需要监听可以不重新该方法
            L.i("setLoadingDataListener onRefresh");
        }

        @Override
        public void onStart() {
            //监听http请求开始，如果不需要监听可以不重新该方法
            L.i("setLoadingDataListener onStart");
        }

        @Override
        public void onSuccess(Bean o ,,Headers headers) {
            //监听http请求成功，如果不需要监听可以不重新该方法
            L.i("setLoadingDataListener onSuccess: " + o);
        }

        @Override
        public void onFailure() {
             //监听http请求失败，如果不需要监听可以不重新该方法
            L.i("setLoadingDataListener onFailure");
        }
});


//添加头
pullToLoadMoreRecyclerView.putHeader(key,value);
//添加请求参数
pullToLoadMoreRecyclerView.putParam(key, value);
//开始请求
pullToLoadMoreRecyclerView.requestData();


//控制加状态，可以在监听中处理
    loadMoreViewHolder.loading("加载中...");//默认文字："加载中..."
    loadMoreViewHolder.loadingFinish("没有更多数据");
```

### 高级用法
```java
//如果使用数字控制页数可以使用如下api
//设置参数的key
setCurPageKey("curPage");//当前页key
setPageSizeKey("pageSize");//每一页数量的key
//设置参数的值
setPageSize(1);//设置每一页数量
setTotalPage(20);//设置一共有多少页
//如果使用其他方式分页
可以设置setLoadingDataListener在onSuccess(Bean o)回调中处理分页
onSuccess回调在每一次加载数据成功后回调
```



### RecyclerView多条目展示
```java
class MyTypeAdapter extends BaseRecyclerAdapter {
    private final int ITEM_TYPE_1 = 0;
    private final int ITEM_TYPE_2 = 1;

    public MyTypeAdapter(RecyclerView recyclerView, List datas) {
        super(recyclerView, null, 0, datas);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //选择类型
        if (viewType == ITEM_TYPE_2) {
            return new MyTypeHolder2(parent, R.layout.item_type2);
        }
        return new MyTypeHolder1(parent, R.layout.item_type1);
    }

    @Override
    public int getItemViewType(int position) {
        //根据position返回类型
        return position % 2 == 0 ? ITEM_TYPE_2 : ITEM_TYPE_1;
    }
}
```

### 动态向Adapter中添加数据

```java
//@param isLoadMore 数据是否累加
//@param datas      List数据

adapter.addDatas(true,datas);
```
### 清空Adapter中所以数据(不推荐使用，使用adapter.addDatas(true,datas))可以实现相同的功能

```java
adapter.clearAllData();
```
详细的使用方法在DEMO里面都演示啦,如果你觉得这个库还不错,请赏我一颗star吧~~~ 

欢迎关注微信公众号

![](http://oi5nqn6ce.bkt.clouddn.com/itheima/booster/code/qrcode.png)



