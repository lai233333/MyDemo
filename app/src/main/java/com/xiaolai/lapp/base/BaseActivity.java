package com.xiaolai.lapp.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.callback.OnRetryListener;
import com.xiaolai.lapp.widget.EmptyLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laizhibin on 2017/3/19.
 */
public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;
    /**
     * 刷新控件，注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    protected boolean isRefresh;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initViews();
        initSwipeRefresh();
        requestData(false);
    }

    private void initSwipeRefresh() {
        if(mSwipeRefresh != null){
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);
            mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestData(true);
                }
            });
        }
    }

    protected abstract int attachLayoutRes();

    protected abstract void initViews();

    protected abstract void requestData(boolean isRefresh);

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    @Override
    public void showLoading() {
        if(isRefresh){
            return;
        }
        if (mEmptyLayout != null) {
            mEmptyLayout.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
            isRefresh = false;
        }
        if(mEmptyLayout != null){
            if(mEmptyLayout.getCurrentStatus() == EmptyLayout.STATUS_NO_DATA){
                return;
            }
            mEmptyLayout.hide();
        }
    }

    @Override
    public void showNetError(OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.showNoNet();
            mEmptyLayout.setRetryListener(onRetryListener);
        }
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
            isRefresh = false;
        }
    }

    protected void initToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_black_1000));
        setSupportActionBar(toolbar);
    }
}
