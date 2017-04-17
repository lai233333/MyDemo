package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MoviePhotoBean implements Serializable {
    private int count;
    private int start;
    private int total;
    private List<MoviePhoto> photos;
    private MovieItemBean subject;

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<MoviePhoto> getPhotos() {
        return photos;
    }

    public MovieItemBean getSubject() {
        return subject;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPhotos(List<MoviePhoto> photos) {
        this.photos = photos;
    }

    public void setSubject(MovieItemBean subject) {
        this.subject = subject;
    }
}
