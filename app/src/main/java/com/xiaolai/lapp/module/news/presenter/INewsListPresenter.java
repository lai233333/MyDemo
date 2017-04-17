package com.xiaolai.lapp.module.news.presenter;

import com.xiaolai.lapp.base.IBasePresenter;

/**
 * Created by laizhibin on 2017/3/22.
 */
public interface INewsListPresenter extends IBasePresenter{

    void requestAdData();

    void loadAdData();
}
