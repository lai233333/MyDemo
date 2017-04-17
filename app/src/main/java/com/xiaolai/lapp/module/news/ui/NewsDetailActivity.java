package com.xiaolai.lapp.module.news.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.utils.ShareUtil;
import com.xiaolai.lapp.widget.webview.FullscreenHolder;
import com.xiaolai.lapp.widget.webview.IWebPageView;
import com.xiaolai.lapp.widget.webview.ImageClickInterface;
import com.xiaolai.lapp.widget.webview.MyWebChromeClient;
import com.xiaolai.lapp.widget.webview.MyWebViewClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by laizhibin on 2017/3/27.
 */
public class NewsDetailActivity extends BaseActivity implements IWebPageView {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.wv_detail)
    WebView mWebView;
    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_news_image)
    ImageView mIvIcon;
    @BindView(R.id.iv_news_image_bg)
    ImageView mIvIconBg;
    @BindView(R.id.video_fullView)
    FrameLayout videoFullView;

    private String mUrl;
    // 进度条是否加载到90%
    public boolean mProgress90;
    // 网页是否加载完成
    public boolean mPageFinish;

    private MyWebChromeClient mWebChromeClient;

    private int[] imageIds={R.drawable.image_default_1
            ,R.drawable.image_default_2
            ,R.drawable.image_default_3
            ,R.drawable.image_default_4
            ,R.drawable.image_default_5
            ,R.drawable.image_default_6
            ,R.drawable.image_default_7};


    public static void launchActivity(Activity context,String url) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("URL",url);
        context.startActivity(intent);
        context.overridePendingTransition(0,0);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initViews() {
        mUrl = getIntent().getStringExtra("URL");

        initToolBar(mToolbar, "Detail");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCollapseToolbar.setCollapsedTitleTextColor(Color.BLACK);
        mCollapseToolbar.setExpandedTitleColor(Color.WHITE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mIvIconBg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mIvIconBg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mIvIconBg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    showAnim();
                }
            });
        }else{
            mIvIconBg.setVisibility(View.GONE);
            mIvIcon.animate().alpha(1).setStartDelay(100).start();
            mWebView.animate().alpha(1).setStartDelay(100).start();
        }
        //展示随机图片
        mIvIcon.setImageResource(imageIds[Constant.getRandom(6,0)]);
        initWebView();
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mWebView.loadUrl(mUrl);
    }

    private void initWebView() {
        mProgressBar.setVisibility(View.VISIBLE);
        WebSettings ws = mWebView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        mWebView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        mWebChromeClient = new MyWebChromeClient(this);
        mWebView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        mWebView.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");
        mWebView.setWebViewClient(new MyWebViewClient(this));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showAnim() {
        int cx = (mIvIconBg.getLeft() + mIvIconBg.getRight()) / 2;
        int cy = (mIvIconBg.getTop() + mIvIconBg.getBottom()) / 2;
        int finalRadius = Math.max(mIvIconBg.getWidth(), mIvIconBg.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mIvIconBg, cx, cy, finalRadius/3, finalRadius);
        anim.setDuration(200);
        anim.start();
        mIvIconBg.setImageResource(R.color.pink);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIvIconBg.setVisibility(View.GONE);
                mIvIcon.animate().alpha(1).setStartDelay(100).start();
                mWebView.animate().alpha(1).setStartDelay(100).start();
            }
        });
    }

    @OnClick({R.id.fab_share})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_share:
                ShareUtil.share(this,"瞧一瞧看一看"+mUrl);
                break;
        }
    }

    @Override
    public void hindProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWebView() {
        startProgress90();
    }

    /**
     * 进度条 假装加载到90%
     */
    public void startProgress90() {
        for (int i = 0; i < 900; i++) {
            final int progress = i + 1;
            mProgressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                    if (progress == 900) {
                        mProgress90 = true;
                        if (mPageFinish) {
                            startProgress90to100();
                        }
                    }
                }
            }, (i + 1) * 2);
        }
    }

    /**
     * 进度条 加载到100%
     */
    public void startProgress90to100() {
        for (int i = 900; i <= 1000; i++) {
            final int progress = i + 1;
            mProgressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                    if (progress == 1000) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }, (i + 1) * 2);
        }
    }

    @Override
    public void hindWebView() {
        mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress() {
        mWebView.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressChanged(int newProgress) {
        if (mProgress90) {
            int progress = newProgress * 100;
            if (progress > 900) {
                mProgressBar.setProgress(progress);
                if (progress == 1000) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void addImageClickListener() {
// 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                //  "objs[i].onclick=function(){alert(this.getAttribute(\"has_link\"));}" +
                "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
                "}" +
                "})()");

        // 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        mWebView.loadUrl("javascript:(function(){" +
                "var objs =document.getElementsByTagName(\"a\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){" +
                "window.injectedObject.textClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
                "}" +
                "})()");
    }

    @Override
    public void fullViewAddView(View view) {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void showVideoFullView() {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindVideoFullView() {
        videoFullView.setVisibility(View.GONE);
    }

    public FrameLayout getVideoFullView() {
        return videoFullView;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        mWebView.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoFullView.removeAllViews();
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }
}
