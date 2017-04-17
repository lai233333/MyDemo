package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MovieComment implements Serializable {
    private MovieRatingBean rating;
    private int useful_count;
    private MovieAuthorBean author;
    private String subject_id;
    private String content;
    private String created_at;
    private String id;

    public int getUseful_count() {
        return useful_count;
    }

    public MovieAuthorBean getAuthor() {
        return author;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }

    public void setUseful_count(int useful_count) {
        this.useful_count = useful_count;
    }

    public void setAuthor(MovieAuthorBean author) {
        this.author = author;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MovieRatingBean getRating() {
        return rating;
    }

    public void setRating(MovieRatingBean rating) {
        this.rating = rating;
    }

}
