package com.xiaolai.lapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.callback.OnRetryListener;
import com.xiaolai.lapp.widget.EmptyLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laizhibin on 2017/3/17.
 */
public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {

    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;
    @Nullable
    @BindView(R.id.swipe_refresh)
   protected SwipeRefreshLayout mSwipeRefresh;

    private boolean isVisible;
    private boolean isPrepared;
    protected boolean isFirstIn = true;
    protected boolean isRefresh;
    private View mRootView;

    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(attachLayoutRes(), null);
            ButterKnife.bind(this, mRootView);
            initView();
            initSwipeRefresh();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        isPrepared = true;
        lazyLoad();
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInVisible();
        }
    }

    protected void onInVisible() {
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad(){
        if(!isPrepared || !isVisible || !isFirstIn){
            return;
        }
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

    protected abstract void initView();

    protected abstract void requestData(boolean isRefresh);

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.onResume();
        }
    }

    @Override
    public void onDestroy() {
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
        if (mEmptyLayout != null) {
            if(mEmptyLayout.getCurrentStatus() == EmptyLayout.STATUS_NO_DATA){
                return;
            }
            mEmptyLayout.hide();
        }
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
            isRefresh = false;
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
        ((BaseActivity)getActivity()).initToolBar(toolbar,title);
    }
}
