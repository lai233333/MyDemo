package com.xiaolai.lapp.base;

import com.xiaolai.lapp.callback.OnRetryListener;
import com.xiaolai.lapp.callback.RequestCallBack;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;

/**
 * Created by laizhibin on 2017/3/17.
 */
public class BasePresenterImpl<T extends IBaseView, V> implements IBasePresenter, RequestCallBack<V>,OnRetryListener {

    protected Subscription mSubscription;
    protected T mView;
    protected int page;
    protected int startPage = 1;

    public BasePresenterImpl(T view) {
        mView = view;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void requestData() {
        page = startPage;
    }

    @Override
    public void requestMoreData() {
        page++;
    }

    @Override
    public void beforeRequest() {
        if(page>startPage){
            return;
        }
        mView.showLoading();
    }

    @Override
    public void requestError(String msg) {
        mView.showNetError(this);
    }

    @Override
    public void requestComplete() {
        mView.hideLoading();
    }

    @Override
    public void requestSuccess(V data) {

    }

    @Override
    public void onRetry() {
        requestData();
    }
}
