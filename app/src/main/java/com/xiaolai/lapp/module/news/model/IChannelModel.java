package com.xiaolai.lapp.module.news.model;

import com.xiaolai.lapp.callback.RequestCallBack;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by laizhibin on 2017/3/26.
 */
public interface IChannelModel {

    Subscription requestData(RequestCallBack<Map<Boolean, List<GankTypeInfo>>> callBack);

    Subscription updateData(String typeKey, boolean select);

    Subscription swapData(String fromKey, String toKey, int fromPos, int toPos);
}
