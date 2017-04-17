package com.xiaolai.lapp.module.welfare.model;

import com.xiaolai.lapp.bean.ImageShowBean;
import com.xiaolai.lapp.common.Constant;

import org.litepal.crud.DataSupport;

/**
 * Created by laizhibin on 2017/3/24.
 */
public class LocalImageInfo extends DataSupport implements ImageShowBean{

    private String type;//已收藏 已下载
    private String imagePath;
    private String imageUrl;
    private String desc;
    private String date;

    public String getType() {
        return type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getImgPath() {
        if(Constant.IMAGE_TYPE_COLLECT.equals(type)){
            return imageUrl;
        }else if(Constant.IMAGE_TYPE_DOWNLOAD.equals(type)){
            return imagePath;
        }
        return "";
    }

    @Override
    public String getImageDesc() {
        return desc;
    }

}
