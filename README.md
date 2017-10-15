# PagerLayoutManager

具有分页功能的 Recyclerview 布局管理器，主打分页，可以替代部分场景下的网格布局，线性布局，以及一些简单的ViewPager，但也有一定的局限性，并不能适用于所有场景，请选择性使用。

另外，该库并不是特别完善，欢迎提交 Issues 或者 PR 来协助我完善。

## 1. 效果预览

![a1](../../../Downloads/a1.gif)
![b1](../../../Downloads/b1.gif)

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

| 名称                                | 作用                |
| --------------------------------- | ----------------- |
| /java/PagerGridLayoutManager.java | 水平分页网格布局管理器。      |
| /java/PagerGridSnapHelper.java    | 滚动辅助工具。           |
| /java/PagerConfig.java            | Pager配置，用于开关调试日志。 |

## 4. 使用方法

### 4.1 基本用法

**注意：**

**1. 一定要在先设置 PagerGridLayoutManager， 之后再设置 PagerGridSnapHelper。**  
**2. 注意名称 PagerGridSnapHelper 不是 PagerSnapHelper。**

```java
// 1.水平分页布局管理器
PagerGridLayoutManager layoutManager = new PagerGridLayoutManager(
        2, 3, PagerGridLayoutManager.VERTICAL);
layoutManager.setPageListener(this);    // 设置页面变化监听器
recyclerView.setLayoutManager(layoutManager);

// 2.设置滚动辅助工具
PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
pageSnapHelper.attachToRecyclerView(recyclerView);
```

**页面变化监听器**

```java
// 当总页数确定时的回调
@Override public void onPageSizeChanged(int pageSize) {
    Log.e("TAG", "总页数 = " + pageSize);
}

// 当页面被选中时的回调（从 0 开始）
@Override public void onPageSelect(int pageIndex) {
    Log.e("TAG", "选中页码 = " + (pageIndex+1));
}
```

### 4.2 其他设置

**是否在滚动过程中回调页码变化。**

```java
// 如果不想在滚动个过程中回调页码变化，可以这样设置(v1.1.0-beta 以上版本支持)
layoutManager.setChangeSelectInScrolling(false);
```

**切换滚动方向**

注意：滚动过程中不可切换方向，设置无效。

```java
mLayoutManager.setOrientationType(PagerGridLayoutManager.HORIZONTAL);
```

**打开调试日志。**

```java
// 如果需要查看调试日志可以设置为true，一般情况忽略即可
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
compile 'com.gcssloop.support:pagerlayoutmanager:1.3.1@aar'
```

## 7. 待优化

- [ ] 添加 scrollToPosition。
- [ ] 添加设置滚动速度。

## 作者简介

#### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

#### 个人网站: http://www.gcssloop.com

<a href="http://www.gcssloop.com/info/about/" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width="300"/> </a>

## 更新日志

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