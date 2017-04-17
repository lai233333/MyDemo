package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MovieAuthorBean implements Serializable{
    private String uid;
    private String avatar;
    private String signature;
    private String alt;
    private String id;
    private String name;

    public String getUid() {
        return uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getSignature() {
        return signature;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
