package com.feima.web.hybrid

import android.content.Context
import android.webkit.JavascriptInterface

class AndroidJavaScriptObject(private var mContext: Context? = null) {

    /**
     * 点击图片
     *
     * @param iSrcs
     * @param iIndex
     */
    @JavascriptInterface
    fun imgGalleryClick(iSrcs: String, iIndex: String?) {

    }

    /**
     * 点击视频播放
     *
     * @param iSrc
     */
    @JavascriptInterface
    fun videoPlayClick(iSrc: String?) {

    }

    /**
     * 点击绘本浏览
     *
     * @param iID
     */
    @JavascriptInterface
    fun drawBookClick(iID: String?) {

    }


    /**
     * 点击跳转纪念册
     *
     * @param menu
     */
    @JavascriptInterface
    fun openMenu(menu: String) {

    }
}