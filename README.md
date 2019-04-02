# Ganks

👌一个简单的Gank.io Demo, 主要用于玩一些优秀的开源库

![](https://github.com/xianfeng92/Ganks/blob/master/images/bugbug.gif)

----------------------------------------------------

[![API](https://img.shields.io/badge/API-28%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=28)

----------------------------------------------------

## MVP

[Mvp 是 Google 官方出品的 Mvp 架构项目,Demo可参考todo-mvp](https://github.com/googlesamples/android-architecture/tree/todo-mvp/)

框架的主要结构如下:

通过 Contract 来管理 View Model Presenter 接口:

* Model -- 主要处理业务, 如网络数据的请求以及存储
* View --  用于把数据展示,并且提供交互
* Presenter -- View 和 Model 交互的桥梁, 二者通过 Presenter 建立联系

MVP框架通过引入接口 View, 让相应的视图组件如 Activity, Fragment 去实现 View, 此时涉及到数据展示的部分交给了View来处理,即实现了视图层的独立.通过中间层Preseter实现了 Model 和 View 的完全解耦。

从此显示交给 View, 逻辑交给 Presenter, 数据交给 Model. 

Activity 和 Fragment 又可以瘦下来了~~

---------------------------------------------------
## Rxjava2 + Retrofit

异步和网络请求的不二之选, 后期计划封装一下 Rxjava2 + Retrofit

--------------------------------------------------
## Fragmentation

Fragmentation 封装了 Fragment 的管理,很 powerful 的一个项目

使用 delegate + interface hook 上系统的 Fragment 的生命周期的回调,然后起飞~

[Fragmentation API](https://github.com/YoKeyword/Fragmentation/wiki/2.-API)

-------------------------------------------------
## AndroidAutoSize

[今日头条屏幕适配方案终极版,一个极低成本的 Android 屏幕适配方案](https://github.com/JessYanCoding/AndroidAutoSize)

今日头条适配方案的原理来源于修改DisplayMetrics#density,因为 DisplayMetrics#density 是全局的,所以只要 DisplayMetrics#density 一经修改,项目中的所有页面、所有控件都可以奏效,包括三方库控件和系统控件,这就使今日头条屏幕适配方案天然拥有优于其他屏幕适配方案的低成本和低侵入性

在Gank 中手动撸了一把 AndroidAutoSize 的简化版的实现,其核心也就是在不同尺寸和分辨率的设备上,根据设计图的总宽度,
强行修改 density 值,来完成屏幕的适配, 完成这个其实就是几行代码的事, AndroidAutoSize 能够将其封装成几千行代码,实属优秀.

我们知道 DisplayMetrics#density 是公有的,谁都有权限修改, AndroidAutoSize 可以把 DisplayMetrics#density 修改成一个可以完成屏幕适配的值,其他三方库、Android 系统、以及项目成员就可以把 DisplayMetrics#density 修改或恢复成另一个值,这都将导致屏幕适配的失效,特别是在某些定制系统上,因为这个定制系统做的某些特殊操作都是未知的. 还有当AndroidAutoSize遇到屏幕差异很大的机型,显示的效果就不尽如人意了.

个人觉得可以综合考虑: smallestWidth适配 + AndroidAutoSize 可能是更好的选择 + 屏幕尺寸差异大的机型控制UI布局

-------------------------------------------------
## imageloader

自己对 Glide 进行简单的封装, 后期还会继续优化


-----------------------------------------------
## BaseRecyclerViewAdapterHelper

一个强大的RecyclerAdapter框架,集成了大部分列表常用需求解决方案.

[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

-------------------------------------------------
## Skeleton

Skeleton Screen Loading，中文叫做骨架屏,即表示在页面完全渲染完成之前,用户会看到一个占位的样式,
用以描绘了当前页面的大致框架,加载完成后,最终骨架屏中各个占位部分将被真实的数据替换。

[Skeleton](https://github.com/ethanhua/Skeleton)

--------------------------------------------------
## LeakCanary

Android 和 Java 内存泄露检测

--------------------------------------------------

# Display

[App 运行效果图](https://github.com/xianfeng92/Ganks/blob/master/images/Display.md)
  

# Thanks

[干货集中营](https://gank.io/api)
