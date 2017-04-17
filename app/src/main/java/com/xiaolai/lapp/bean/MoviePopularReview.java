package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MoviePopularReview implements Serializable {
    private MovieRatingBean rating;
    private MovieAuthorBean author;
    private String title;
    private String subject_id;
    private String summary;
    private String alt;
    private String id;

    private int useful_count;
    private String content;
    private String created_at;
    private String updated_at;
    private String share_url;
    private int useless_count;
    private int comments_count;

    public MovieRatingBean getRating() {
        return rating;
    }

    public MovieAuthorBean getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public String getSummary() {
        return summary;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public void setRating(MovieRatingBean rating) {
        this.rating = rating;
    }

    public void setAuthor(MovieAuthorBean author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUseful_count() {
        return useful_count;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getShare_url() {
        return share_url;
    }

    public int getUseless_count() {
        return useless_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setUseful_count(int useful_count) {
        this.useful_count = useful_count;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setUseless_count(int useless_count) {
        this.useless_count = useless_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }
}
