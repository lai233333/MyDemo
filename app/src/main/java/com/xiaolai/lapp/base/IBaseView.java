package com.xiaolai.lapp.base;

import com.xiaolai.lapp.callback.OnRetryListener;

/**
 * Created by laizhibin on 2017/3/17.
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    void showNetError(OnRetryListener onRetryListener);
}
