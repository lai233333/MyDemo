package com.xiaolai.lapp.module.news.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/19.
 */
public interface INewsMainView extends IBaseView {

    void loadData(List<GankTypeInfo> data);

    void registerRxBusEvent();
}
