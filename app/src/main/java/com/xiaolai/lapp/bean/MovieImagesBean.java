package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class MovieImagesBean implements Serializable {
    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
