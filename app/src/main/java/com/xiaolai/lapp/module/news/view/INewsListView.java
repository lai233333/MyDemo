package com.xiaolai.lapp.module.news.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.FrontPageBean;
import com.xiaolai.lapp.bean.GankItemBean;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/22.
 */
public interface INewsListView extends IBaseView {

    void loadData(List<GankItemBean> data);

    void loadMoreData(List<GankItemBean> data);

    void loadAdData(List<FrontPageBean.DataBeanX.DataBean> adData);
}
