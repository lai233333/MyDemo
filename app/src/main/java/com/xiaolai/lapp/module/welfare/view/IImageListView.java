package com.xiaolai.lapp.module.welfare.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.ImageShowBean;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/24.
 */
public interface IImageListView extends IBaseView{

    void loadData(List<? extends ImageShowBean> imageList);
}
