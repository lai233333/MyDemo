package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class MovieItemBean implements Serializable{
    private MovieRatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private MovieImagesBean images;
    private String alt;
    private String id;
    private boolean has_video;
    private String mainland_pubdate;
    private List<String> genres;
    private List<String> durations;
    private List<String> pubdates;
    private List<MoviePersonBean> casts;
    private List<MoviePersonBean> directors;

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }

    public void setPubdates(List<String> pubdates) {
        this.pubdates = pubdates;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public List<String> getDurations() {
        return durations;
    }

    public List<String> getPubdates() {
        return pubdates;
    }

    public void setRating(MovieRatingBean rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImages(MovieImagesBean images) {
        this.images = images;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setCasts(List<MoviePersonBean> casts) {
        this.casts = casts;
    }

    public void setDirectors(List<MoviePersonBean> directors) {
        this.directors = directors;
    }

    public MovieRatingBean getRating() {

        return rating;
    }

    public String getTitle() {
        return title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getYear() {
        return year;
    }

    public MovieImagesBean getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<MoviePersonBean> getCasts() {
        return casts;
    }

    public List<MoviePersonBean> getDirectors() {
        return directors;
    }
}
