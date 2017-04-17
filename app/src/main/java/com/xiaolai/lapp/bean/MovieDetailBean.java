package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class MovieDetailBean implements Serializable{

    private MovieRatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;
    private MovieImagesBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private int do_count;
    private String share_url;
    private int seasons_count;
    private String schedule_url;
    private int episodes_count;
    private int collect_count;
    private String current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private int photos_count;
    private String pubdate;
    private String mainland_pubdate;
    private boolean has_video;
    private boolean has_schedule;
    private boolean has_ticket;
    private List<String> languages;
    private List<String> pubdates;
    private List<String> tags;
    private List<String> countries;
    private List<String> genres;
    private List<String> durations;
    private List<MoviePersonBean> casts;
    private List<MoviePersonBean> directors;
    private List<MoviePersonBean> writers;
    private List<String> aka;
    private List<MovieComment> popular_comments;
    private List<MovieTrailer> trailers;
    private List<String> trailer_urls;
    private List<MoviePhoto> photos;
    private List<MoviePopularReview> popular_reviews;

    public void setRating(MovieRatingBean rating) {
        this.rating = rating;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
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

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDo_count(int do_count) {
        this.do_count = do_count;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setSeasons_count(int seasons_count) {
        this.seasons_count = seasons_count;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public void setEpisodes_count(int episodes_count) {
        this.episodes_count = episodes_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public void setCurrent_season(String current_season) {
        this.current_season = current_season;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
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

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public MovieRatingBean getRating() {

        return rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public String getDouban_site() {
        return douban_site;
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

    public String getMobile_url() {
        return mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public int getDo_count() {
        return do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public int getSeasons_count() {
        return seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public int getEpisodes_count() {
        return episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public String getCurrent_season() {
        return current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getSummary() {
        return summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public List<String> getCountries() {
        return countries;
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

    public List<String> getAka() {
        return aka;
    }

    public int getPhotos_count() {
        return photos_count;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public boolean isHas_schedule() {
        return has_schedule;
    }

    public boolean isHas_ticket() {
        return has_ticket;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getPubdates() {
        return pubdates;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getDurations() {
        return durations;
    }

    public List<MoviePersonBean> getWriters() {
        return writers;
    }

    public List<MovieComment> getPopular_comments() {
        return popular_comments;
    }

    public List<MovieTrailer> getTrailers() {
        return trailers;
    }

    public List<String> getTrailer_urls() {
        return trailer_urls;
    }

    public List<MoviePhoto> getPhotos() {
        return photos;
    }

    public List<MoviePopularReview> getPopular_reviews() {
        return popular_reviews;
    }

    public void setPhotos_count(int photos_count) {
        this.photos_count = photos_count;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public void setHas_schedule(boolean has_schedule) {
        this.has_schedule = has_schedule;
    }

    public void setHas_ticket(boolean has_ticket) {
        this.has_ticket = has_ticket;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void setPubdates(List<String> pubdates) {
        this.pubdates = pubdates;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }

    public void setWriters(List<MoviePersonBean> writers) {
        this.writers = writers;
    }

    public void setPopular_comments(List<MovieComment> popular_comments) {
        this.popular_comments = popular_comments;
    }

    public void setTrailers(List<MovieTrailer> trailers) {
        this.trailers = trailers;
    }

    public void setTrailer_urls(List<String> trailer_urls) {
        this.trailer_urls = trailer_urls;
    }

    public void setPhotos(List<MoviePhoto> photos) {
        this.photos = photos;
    }

    public void setPopular_reviews(List<MoviePopularReview> popular_reviews) {
        this.popular_reviews = popular_reviews;
    }
}
