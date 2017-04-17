package com.xiaolai.lapp.module.welfare.model;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.xiaolai.lapp.callback.OnCompleteListener;
import com.xiaolai.lapp.callback.SimpleOnCompleteListener;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.welfare.db.LocalImageDb;
import com.xiaolai.lapp.utils.DownloadUtil;
import com.xiaolai.lapp.utils.TimeUtil;
import com.xiaolai.lapp.utils.ToastUtil;

import org.litepal.crud.DataSupport;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareDetailModelImpl implements IWelfareDetailModel {

    @Override
    public void downloadImage(String url, OnCompleteListener listener) {
        DownloadUtil.downloadImage(url, listener);
    }

    @Override
    public boolean collectImage(String url) {
        LocalImageInfo imageInfo = new LocalImageInfo();
        imageInfo.setDesc(Constant.poetry.get((int)(0+Math.random()*(Constant.poetry.size()-1-0+1))));
        imageInfo.setType(Constant.IMAGE_TYPE_COLLECT);
        imageInfo.setDate(TimeUtil.getDataFull());
        imageInfo.setImageUrl(url);
        return imageInfo.save();
    }
}
