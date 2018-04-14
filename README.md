# PagerLayoutManager

具有分页功能的 Recyclerview 布局管理器，主打分页，可以替代部分场景下的网格布局，线性布局，以及一些简单的ViewPager，但也有一定的局限性，请选择性使用。

- [网格分页布局源码解析(上)](https://xiaozhuanlan.com/topic/5841730926)
- [网格分页布局源码解析(下)](https://xiaozhuanlan.com/topic/1456397082)

## 1. 效果预览

![](http://ww1.sinaimg.cn/large/005Xtdi2gy1fq3xnpt8fhg308c0e6jwo.gif) ![](http://ww1.sinaimg.cn/large/005Xtdi2gy1fq3xxgvf7lg308c0e6juv.gif)

![](http://ww1.sinaimg.cn/large/005Xtdi2gy1fkjcpnh4wzg308c0ep1kz.gif) ![](http://ww1.sinaimg.cn/large/005Xtdi2gy1fkjcq86gyqg308c0epb2a.gif)



## 2. 支持的特性

- [x] 网格布局，设置合适的行列数可以模拟线性布局或者ViewPager。
- [x] 分页效果，支持自动回调页码的变化(总页数和当前页数)。
- [x] 布局方向，支持横向滚动和垂直滚动。
- [x] 方向切换，支持切换滚动方向。
- [x] 支持电视，支持TV上按键翻页。
- [x] 内存稳定，不会因为一次性添加大量的数据导致内存飙升或者严重卡顿。
- [x] 使用简单，无侵入性，可以快速的将其他布局替换为该布局，也可以快速的移除该布局。
- [x] 自动修正，当滚动到第3页，删除数据后内容不足2页，会自动修正当前页面为第2页。

## 3. 主要文件

| 名称                              | 作用                          |
| --------------------------------- | ----------------------------- |
| /java/PagerGridLayoutManager.java | 水平分页网格布局管理器。      |
| /java/PagerGridSnapHelper.java    | 滚动辅助工具。                |
| /java/PagerConfig.java            | Pager配置，用于开关调试日志。 |



## 4. 使用方法

### 4.1 基本用法

#### 4.1.1 基本设置

```java
// 1.水平分页布局管理器
PagerGridLayoutManager layoutManager = new PagerGridLayoutManager(
        2, 3, PagerGridLayoutManager.VERTICAL);
recyclerView.setLayoutManager(layoutManager);

// 2.设置滚动辅助工具
PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
pageSnapHelper.attachToRecyclerView(recyclerView);
```

**注意：**  
**1. 一定要在先设置 PagerGridLayoutManager， 之后再设置 PagerGridSnapHelper。**  
**2. 注意名称是 PagerGridSnapHelper 不是 PagerSnapHelper。**

#### 4.1.2 设置页面变化监听器

```java
layoutManager.setPageListener(this);    // 设置页面变化监听器

// 当总页数确定时的回调
@Override public void onPageSizeChanged(int pageSize) {
    Log.e("TAG", "总页数 = " + pageSize);
}

// 当页面被选中时的回调（从 0 开始）
@Override public void onPageSelect(int pageIndex) {
    Log.e("TAG", "选中页码 = " + (pageIndex+1));
}
```

#### 4.1.3 直接滚动

```java
// 滚动到指定条目
public void scrollToPosition(int position);
// 切换页面
public void scrollToPage(int pageIndex);	// 滚动到指定页面
public void prePage();						// 上一页
public void nextPage();						// 下一页

// 使用示例
recyclerView.scrollToPosition(0);
layoutManager.scrollToPage(0);
layoutManager.prePage();
layoutManager.nextPage();
```

#### 4.1.4 平滑滚动

```java
// 平滑滚动到指定条目
mRecyclerView.smoothScrollToPosition(pos);
// 平滑切换页面
mPagerLyoutManager.smoothScrollToPage(pageIndex);	// 平滑滚动到指定页
mPagerLyoutManager.smoothPrePage();					// 平滑滚动到上一页
mPagerLyoutManager.smoothNextPage();				// 平滑滚动到下一页

// 使用示例
recyclerView.smoothScrollToPosition(0);
layoutManager.smoothScrollToPage(0);
layoutManager.smoothPrePage();
layoutManager.smoothNextPage();
```

### 4.2 其他设置

#### 4.2.1 是否在滚动过程中回调页码变化

```java
// 设置是否在滚动过程中回调页码变化
void setChangeSelectInScrolling(boolean changeSelectInScrolling);

// 使用示例
layoutManager.setChangeSelectInScrolling(false);
```

#### 4.2.2 是否允许连续滚动

默认允许连续滚动。

```java
// 是否允许连续滚动
boolean isAllowContinuousScroll();
// 设置是否允许连续滚动
void setAllowContinuousScroll(boolean allowContinuousScroll);

// 使用示例
layoutManager.isAllowContinuousScroll();
layoutManager.setAllowContinuousScroll(false);
```

#### 4.2.3 设置滚动方向

注意：滚动过程中不可切换方向，设置无效。

```java
// 设置滚动方向
int setOrientationType(@OrientationType int orientation);

// 使用示例
layoutManager.setOrientationType(PagerGridLayoutManager.HORIZONTAL);
```

#### 4.2.4 设置 Fling 阀值

```java
// 设置 Fling 阀值
void setFlingThreshold(int flingThreshold);

// 使用示例
PagerConfig.setFlingThreshold(1000);
```

#### 4.2.5 设置滚动速度

```java
// 设置滚动速度(滚动一英寸所耗费的微秒数，数值越大，速度越慢，默认为 60f)
void setMillisecondsPreInch(float millisecondsPreInch);

// 使用示例
PagerConfig.setMillisecondsPreInch(60f);
```

#### 4.2.6 打开调试日志

```java
// 打开调试用日志输出，一般情况忽略即可
void setShowLog(boolean showLog);

// 使用示例
PagerConfig.setShowLog(true);
```



## 5. 注意事项：

目前存在一个问题，使用的时候请务必给 RecyclerView 设置固定大小或者match_parent，如果不设置默认高度为 0 ，则任何内容都不会显示出来。

## 6. 添加方式

#### 6.1 添加仓库

在项目的 `build.gradle` 文件中配置仓库地址。

```groovy
allprojects {
    repositories {
        jcenter()
        // 私有仓库地址
       maven { url "http://lib.gcssloop.com/repository/gcssloop-central/" }
    }
}
```

#### 6.2 添加项目依赖

在需要添加依赖的 Module 下添加以下信息，使用方式和普通的远程仓库一样。

```groovy
implementation 'com.gcssloop.recyclerview:pagerlayoutmanager:2.3.8'
```



## 作者简介

#### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

#### 个人网站: http://www.gcssloop.com

<a href="http://www.gcssloop.com/info/about/" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width="300"/> </a>

## 更新日志

#### v2.3.8

修复 RecyclerView 设置为 wrap_content 时不显示(当属性为 wrap_content 时让其填充父窗体)。

#### v2.3.7

完善代码细节，移除冗余逻辑。

#### v2.3.6

降低最低版本兼容。  
完善文档注释，精简项目结构。

#### v2.3.5

优化代码结构，抽取 PagerGridSmoothScroller。

#### v2.3.4

修复 padding 问题。  
优化计算显示区域的代码逻辑。

#### v2.3.3

优化代码结构。  
移除冗余代码。

#### v2.3.2

调整平滑滚动对外接口。

```java
// 平滑滚动到上一页
mPagerLyoutManager.smoothPrePage();
// 平滑滚动到下一页
mPagerLyoutManager.smoothNextPage();
// 平滑滚动到指定页
mPagerLyoutManager.smoothScrollToPage(pageIndex);
```

#### v2.3.1

优化直接滚动。  
修复更新单个条目导致整个界面刷新的情况。

#### v2.3.0

添加平滑滚动，优化超长距离平滑滚动。  

```java
// 平滑滚动到指定条目，通过 RecyclerView 调用
mRecyclerView.smoothScrollToPosition(pos);

// 平滑滚动到上一页
mPagerLyoutManager.smoothPrePage(recyclerView);
// 平滑滚动到下一页
mPagerLyoutManager.smoothNextPage(recyclerView);
// 平滑滚动到指定页
mPagerLyoutManager.smoothScrollToPage(recyclerView, pageIndex);
```

#### V2.2.4

优化滚动体验。  
新增配置选项：

```java
// 是否允许连续滚动
boolean isAllowContinuousScroll();
// 设置是否允许连续滚动
void setAllowContinuousScroll(boolean allowContinuousScroll);
```

#### v2.2.3

优化滚动过程中页面回调逻辑。

#### v2.2.2

修复除零异常。

#### v2.2.1

修复直接滚动到指定页面的Bug。  
添加设置快速滚动阀值的方法 `snapHelper.setFlingThreshold(1000);`  
修复不设置页面监听器会导致显示不正常的问题。
修复行列为1时滚动出现的问题。

#### v2.2.0(该版本不稳定)

新增方法：

```java
// 滚动到指定条目
public void scrollToPosition(int position);
// 滚动到指定页面
public void scrollToPage(int pageIndex);
// 上一页
public void prePage();
// 下一页
public void nextPage();
```

#### v2.1.1

修复使用Glide或者Fresco可能导致布局重复刷新的问题。

(判断RecyclerView.State，如果结构状态没有变化则不重新布局)

#### v2.1.0

修复从后向前滚动时内存占用变大的问题。

(产生原因时先添加了新的View，最后再回收废弃的View，导致被回收的View一直难以被复用，持续占用内存，正确的应该是先将废弃的View放入回收区，这样新View创建时直接从回收区取View，就避免了回收区堆积大量的废弃View。)

#### v2.0.0

统一包名(功能没有变化)。

#### v1.3.1

修复特殊情况下删除条目导致显示不完整的问题。

#### v1.3.0

支持动态切换滚动方向。

#### v1.2.2

修复条目Margin问题。

#### v1.2.1

修复慢速滚动偶尔会停止在两个页面之间的问题。

#### v1.2.0

修复页面相关的Bug。  
慢速滑动超多半个View时自动滚动到下一页。  
调整布局逻辑和滚动辅助器逻辑。  
移除部分无效方法。  
代码结构调整。  
支持按键翻页(TV)。  

**在TV上快速滚动时存在焦点问题：**

前置条件：竖向滚动，当下方有可以获取焦点控件。  
预期结果：当有内容时向下滚动，当滚动到最后一页时，焦点才能移动到下面控件上。  
实际结果：当慢速滚动时符合预期，当快速滚动时有可能在移动到最后一页之前就将焦点移动到下面的控件上。  

#### v1.1.3

修正页面变化回调位置，将回调修正到布局之后。  
修正页面数据变化回调逻辑，允许多次回调同一个页面数据。

#### v1.1.2

修复内容删除完毕时没有页面变化回调问题。

#### v1.1.1

修复删除完毕最后一页作品，页面显示空白，滑动卡顿的bug。

#### v1.1.0

beta测试通过。  
整理代码结构。

#### v1.1.0-beta

添加在滚动过程中回调页码变化。

#### v1.0.1

修复数据过少导致的数组越界异常。

#### v1.0.0

移除部分日志。  
代码结构整理。

#### v1.0.0-beta1

修复错误回滚问题。`例如：本应滚动到第二页，却在滚动结束后回滚到第一页。`

#### v1.0.0-beta

完成基本的分页功能。  
完成分页滚动辅助工具。  
滑动结束自动锁定到最近页面。  
每次滚动仅允许滚动一页，防止连续滚动多页。  
低内存占用。  
支持水平滚动和垂直分页。  
允许开关调试日志。

## 版权信息

```
Copyright (c) 2017 GcsSloop

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

