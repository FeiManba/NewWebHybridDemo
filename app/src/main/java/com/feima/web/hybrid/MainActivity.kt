package com.feima.web.hybrid

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mCustomView: View? = null
    private var mCustomViewCallBack: WebChromeClient.CustomViewCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settings = web_view.settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                if (mCustomView != null) {
                    callback?.onCustomViewHidden()
                    return
                }
                mCustomView = view
                mCustomView?.visibility = View.VISIBLE
                mCustomViewCallBack = callback
                fl_video.addView(mCustomView)
                fl_video.visibility = View.VISIBLE
                fl_video.bringToFront()
                //设置横屏
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                if (mCustomView == null) {
                    return
                }
                mCustomView?.visibility = View.GONE
                fl_video.removeView(mCustomView)
                mCustomView = null
                fl_video.visibility = View.GONE
                mCustomViewCallBack?.onCustomViewHidden()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        // 不使用缓存
        // settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 启用js交互
        //        settings.javaScriptEnabled = true
        //        web_view.addJavascriptInterface(
        //            AndroidJavaScriptObject(this),
        //            "growingjs"
        //        )
        //支持自动加载图片
        settings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        //设置适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //        //支持缩放
        //        settings.setSupportZoom(true)
        //        settings.builtInZoomControls = true
        //        //隐藏原生的缩放控件
        //        settings.displayZoomControls = false
        //        //支持内容重新布局
        //        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //        settings.supportMultipleWindows()
        //        settings.setSupportMultipleWindows(true)
        //        //设置缓存模式
        //        settings.domStorageEnabled = true
        //        settings.databaseEnabled = true
        //        settings.cacheMode = WebSettings.LOAD_DEFAULT
        //        settings.setAppCacheEnabled(true)
        //        settings.setAppCachePath(web_view.context.cacheDir.absolutePath)

        //设置可访问文件
        //        settings.allowFileAccess = true
        //当webview调用requestFocus时为webview设置节点
        //当webview调用requestFocus时为webview设置节点
        //        settings.setNeedInitialFocus(true)
        web_view.loadUrl("http://detail.zaichengzhang.net/news/article/b2d5a174-d833-4488-8e81-3a8f78ebf332")
        //        web_view.loadUrl("http://www.zaichengzhang.net")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //清空所有cookie
        //清空所有cookie
        CookieSyncManager.createInstance(this)
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
        CookieSyncManager.getInstance().sync()
        web_view.webChromeClient = null
        web_view.settings.javaScriptEnabled = false
        web_view.clearCache(true)
        web_view.destroy()
    }
}