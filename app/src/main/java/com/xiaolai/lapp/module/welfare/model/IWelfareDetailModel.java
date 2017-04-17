package com.xiaolai.lapp.module.welfare.model;

import com.xiaolai.lapp.callback.OnCompleteListener;
import com.xiaolai.lapp.callback.RequestCallBack;

/**
 * Created by laizhibin on 2017/3/23.
 */
public interface IWelfareDetailModel {

    void downloadImage(String url, OnCompleteListener listener);

    boolean collectImage(String url);
}
