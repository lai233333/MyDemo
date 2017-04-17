package com.xiaolai.lapp.module.welfare.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.GankItemBean;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/23.
 */
public interface IWelfareView extends IBaseView {

    void loadData(List<GankItemBean> data);

    void loadMoreData(List<GankItemBean> data);
}
