package com.xiaolai.lapp.module.welfare.view;

import com.xiaolai.lapp.base.IBaseView;

/**
 * Created by laizhibin on 2017/3/23.
 */
public interface IWelfareDetailView extends IBaseView {

    void registerActivityFinish();

    void downloadSuccess(String path);

    void collectSuccess(boolean success);
}
