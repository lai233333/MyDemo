package com.xiaolai.lapp.bean;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/29.
 */
public class MoviePhoto implements Serializable {
    private String thumb;
    private String image;
    private String cover;
    private String alt;
    private String id;
    private String icon;

    private int photos_count;
    private MovieAuthorBean author;
    private String created_at;
    private String album_id;
    private String prev_photo;
    private String next_photo;
    private String album_url;
    private int comments_count;
    private int recs_count;
    private int position;
    private String album_title;
    private String subject_id;
    private String desc;

    public String getThumb() {
        return thumb;
    }

    public String getImage() {
        return image;
    }

    public String getCover() {
        return cover;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPhotos_count() {
        return photos_count;
    }

    public MovieAuthorBean getAuthor() {
        return author;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public String getPrev_photo() {
        return prev_photo;
    }

    public String getNext_photo() {
        return next_photo;
    }

    public String getAlbum_url() {
        return album_url;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getRecs_count() {
        return recs_count;
    }

    public int getPosition() {
        return position;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setPhotos_count(int photos_count) {
        this.photos_count = photos_count;
    }

    public void setAuthor(MovieAuthorBean author) {
        this.author = author;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public void setPrev_photo(String prev_photo) {
        this.prev_photo = prev_photo;
    }

    public void setNext_photo(String next_photo) {
        this.next_photo = next_photo;
    }

    public void setAlbum_url(String album_url) {
        this.album_url = album_url;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public void setRecs_count(int recs_count) {
        this.recs_count = recs_count;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
