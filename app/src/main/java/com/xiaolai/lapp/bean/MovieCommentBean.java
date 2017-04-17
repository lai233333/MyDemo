package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MovieCommentBean implements Serializable {
    private int count;
    private int start;
    private int total;
    private int next_start;
    private List<MovieComment> comments;
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

    public int getNext_start() {
        return next_start;
    }

    public List<MovieComment> getComments() {
        return comments;
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

    public void setNext_start(int next_start) {
        this.next_start = next_start;
    }

    public void setComments(List<MovieComment> comments) {
        this.comments = comments;
    }

    public void setSubject(MovieItemBean subject) {
        this.subject = subject;
    }
}
