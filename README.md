# Ganks

👌This is a simple app using the Uncle Bob's clean architecture approach.

![](https://github.com/xianfeng92/Ganks/blob/master/images/bugbug.gif)

----------------------------------------------------

# Progress

![Progress](http://progressed.io/bar/20)

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

### LifeCycle about Ganks

see:

[When we cool start and entering the HomeFragment](https://github.com/xianfeng92/Ganks/blob/master/notes/LifeCycle.md)

[when entering the TanTanFragment](https://github.com/xianfeng92/Ganks/blob/master/notes/TanTanFragment.md)

---------------------------------------------------
# Display

[App Display](https://github.com/xianfeng92/Ganks/blob/master/images/Display.md)

-------------------------------------------------

## Library Used

#### 1. [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

       A powerful Recycler Adapter framework that integrates most common list requirements solutions.

#### 2. [Fragmentation](https://github.com/YoKeyword/Fragmentation/wiki/2.-API)

      A powerful library that manage Fragment for Android!

#### 3. [Retrofit](https://github.com/square/retrofit)

      Type-safe HTTP client for Android and Java by Square

#### 4. [Rxjava](https://github.com/ReactiveX/RxJava)

       RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences 
       for the Java VM.
       
#### 5.  [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

A low-cost Android screen adaptation solution

#### 6.  [LeakCanary](https://github.com/square/leakcanary)

     A memory leak detection library for Android and Java.

-------------------------------------------------------
# Thanks

[Gank io](https://gank.io/api)

# License

Copyright 2019 xianfeng zhong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
