package com.xiaolai.lapp.module.news.model;

import com.xiaolai.lapp.callback.RequestCallBack;

import java.util.List;

import rx.Subscription;

/**
 * Created by laizhibin on 2017/3/21.
 */
public interface INewsMainModel {

    Subscription getChannelList(RequestCallBack<List<GankTypeInfo>> callBack);
}
