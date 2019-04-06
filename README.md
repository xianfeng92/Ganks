# Ganks

👌This is a simple app using the Uncle Bob's clean architecture approach.

![](https://github.com/xianfeng92/Ganks/blob/master/images/bugbug.gif)

----------------------------------------------------
![](https://img.shields.io/badge/Build-passing-brightgreen.svg)
[![API](https://img.shields.io/badge/API-28%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=28)
![commit](https://img.shields.io/github/commit-activity/m/xianfeng92/Ganks.svg)
![](https://img.shields.io/github/repo-size/xianfeng92/Ganks.svg)
![Author](https://img.shields.io/badge/Author-xianfeng92-brightgreen.svg)

----------------------------------------------------
## Ganks Architecture

![Ganks Architecture](https://github.com/xianfeng92/Ganks/blob/master/images/MVP.jpg)

### some Details

1. Presenters in this layer are composed with interactors that perform the job in a new thread outside the main android UI thread, and come back using a callback with the data that will be rendered in the view.

2. Domain Library a pure java module without any android dependencies. All the external components use interfaces when connecting to the data Library.

3. All data needed for the application comes from data Library , through a MeiziRepository implementation (the interface is in the domain)

---------------------------------------------------

## Library Used

-----------------------------------------------
## ViewAdapter

### BaseRecyclerViewAdapterHelper

A powerful Recycler Adapter framework that integrates most common list requirements solutions.

[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

-----------------------------------------------

## FragmentManager
### Fragmentation

A powerful library that manage Fragment for Android!

[Fragmentation API](https://github.com/YoKeyword/Fragmentation/wiki/2.-API)

----------------------------------------------

## HTTP
### Retrofit

Type-safe HTTP client for Android and Java by Square

[retrofit](https://github.com/square/retrofit)

-----------------------------------------------

## Async
### Rxjava2

RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.

[RxJava](https://github.com/ReactiveX/RxJava)

---------------------------------------------
## ScreenAdapter
###  AndroidAutoSize

A low-cost Android screen adaptation solution
[A low-cost Android screen adaptation solution](https://github.com/JessYanCoding/AndroidAutoSize)

-------------------------------------------------
###  ImageLoader

Simple wapper of Glide by me, needs  further optimized.

-------------------------------------------------

## Memory

### LeakCanary

A memory leak detection library for Android and Java.

[leakcanary](https://github.com/square/leakcanary)

-----------------------------------------------------

# Tips

## 1. Favor composition over inheritance

------------------------------------------------------

# Display

[AppDisplayPicture](https://github.com/xianfeng92/Ganks/blob/master/images/Display.md)
  

# Thanks

[Gank io](https://gank.io/api)
