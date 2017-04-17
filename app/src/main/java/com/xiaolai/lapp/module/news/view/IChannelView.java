package com.xiaolai.lapp.module.news.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/26.
 */
public interface IChannelView extends IBaseView{

    void loadData(List<GankTypeInfo> selectList, List<GankTypeInfo> unSelectList);
}
