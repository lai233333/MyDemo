package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class HotMovieBean implements Serializable {
    private int count;
    private int start;
    private int total;
    private String title;
    private List<MovieItemBean> subjects;

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubjects(List<MovieItemBean> subjects) {
        this.subjects = subjects;
    }

    public int getCount() {

        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public String getTitle() {
        return title;
    }

    public List<MovieItemBean> getSubjects() {
        return subjects;
    }
}
