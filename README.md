# Ganks

Just for fun，Let's coding

--------------------------------------------------

👌一个简单的Gank.io Demo

----------------------------------------------------

## MVP

[Mvp 是 Google 官方出品的 Mvp 架构项目,Demo可参考todo-mvp](https://github.com/googlesamples/android-architecture/tree/todo-mvp/)

框架的主要结构如下:

通过 Contract 来管理 View Model Presenter 接口:

* Model -- 主要处理业务, 如网络数据的请求以及存储
* View --  用于把数据展示,并且提供交互
* Presenter -- View 和 Model 交互的桥梁, 二者通过Presenter建立联系

MVP框架通过引入接口 View, 让相应的视图组件如 Activity, Fragment 去实现 View, 此时涉及到数据展示的部分交给了View来处理,即实现了视图层的独立.通过中间层Preseter实现了 Model 和 View 的完全解耦。

从此显示交给 View, 逻辑交给 Presenter, 数据交给 Model. 

Activity 和 Fragment 又瘦下来了~~

---------------------------------------------------
## Rxjava2 + Retrofit

网络访问的不二之选

--------------------------------------------------
## Fragmentation

Fragmentation 封装了 Fragment 的管理,很 powerful 的一个项目

使用 delegate + interface hook 上系统的 Fragment 的生命周期的回调,然后起飞~

[Fragmentation API](https://github.com/YoKeyword/Fragmentation/wiki/2.-API)

-------------------------------------------------
## imageloader

主要是对 Glide 进行简单封装,后期还会继续优化


-----------------------------------------------
## BaseRecyclerViewAdapterHelper



-------------------------------------------------

## LeakCanary

Android 和 Java 内存泄露检测


--------------------------------------------------

# Display

  [App 运行效果图](https://github.com/xianfeng92/Ganks/blob/master/images/Display.md)
  

# Thanks

[干货集中营](https://gank.io/api)
