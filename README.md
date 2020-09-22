# #Android WebView 加载H5网页 全屏幕播放视频
---
![](<image src='./screen/20200921174853114.gif'/>) 

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:visibility="gone"
        android:id="@+id/fl_video"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <WebView
        android:id="@+id/web_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
## # 设置WebChromeClient
```kotlin
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
```
```kotlin
	// 检测 屏幕旋转
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
```
