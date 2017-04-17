package com.xiaolai.lapp.base;

/**
 * Created by laizhibin on 2017/3/17.
 */
public interface IBasePresenter {

    void onResume();

    void onDestroy();

    void requestData();

    void requestMoreData();
}
