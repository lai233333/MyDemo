package com.xiaolai.lapp.module.welfare.presenter;

import android.content.Context;

import com.xiaolai.lapp.base.IBasePresenter;

/**
 * Created by laizhibin on 2017/3/23.
 */
public interface IWelfareDetailPresenter extends IBasePresenter{

    void shareImage(Context context, String url);

    void downloadImage(String url);

    void collectImage(String url);
}
