package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MovieTrailer implements Serializable {
    private String medium;
    private String title;
    private String subject_id;
    private String alt;
    private String small;
    private String resource_url;
    private String id;

    public String getMedium() {
        return medium;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public String getAlt() {
        return alt;
    }

    public String getSmall() {
        return small;
    }

    public String getResource_url() {
        return resource_url;
    }

    public String getId() {
        return id;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    public void setId(String id) {
        this.id = id;
    }
}
