package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class MoviePersonBean implements Serializable{

    private String alt;
    private MovieImagesBean avatars;
    private String name;
    private String name_en;
    private String id;

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_en() {

        return name_en;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setAvatars(MovieImagesBean avatars) {
        this.avatars = avatars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlt() {

        return alt;
    }

    public MovieImagesBean getAvatars() {
        return avatars;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
