/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-09-20 16:32:43
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.widget;

/**
 * 作用：Pager配置
 * 作者：GcsSloop
 * 摘要：主要用于Log的显示与关闭
 */
public class PagerConfig {
    private static boolean sShowLog = false;

    /**
     * 判断是否输出日志
     *
     * @return true 输出，false 不输出
     */
    public static boolean isShowLog() {
        return sShowLog;
    }

    /**
     * 设置是否输出日志
     *
     * @param showLog 是否输出
     */
    public static void setShowLog(boolean showLog) {
        sShowLog = showLog;
    }
}
