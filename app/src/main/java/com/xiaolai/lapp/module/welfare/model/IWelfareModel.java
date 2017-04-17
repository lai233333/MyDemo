package com.xiaolai.lapp.module.welfare.model;

import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.callback.RequestCallBack;

import java.util.List;

import rx.Subscription;

/**
 * Created by laizhibin on 2017/3/23.
 */
public interface IWelfareModel {
    Subscription requestData(String id, int page, RequestCallBack<List<GankItemBean>> callBack);
}
