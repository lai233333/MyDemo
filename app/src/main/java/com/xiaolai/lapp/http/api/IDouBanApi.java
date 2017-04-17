package com.xiaolai.lapp.http.api;

import com.xiaolai.lapp.bean.HotMovieBean;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.bean.MovieDetailBean;
import com.xiaolai.lapp.bean.MoviePhotoBean;
import com.xiaolai.lapp.bean.MovieReviewBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by laizhibin on 2017/3/21.
 */
public interface IDouBanApi {
    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("in_theaters")
    Observable<HotMovieBean> getHotMovie(@Query("apikey") String apikey,
                                         @Query("city") String city,
                                         @Query("start") int start,
                                         @Query("count") int count,
                                         @Query("client") String client,
                                         @Query("udid") String udid);

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id,
                                               @Query("apikey") String apikey,
                                               @Query("city") String city,
                                               @Query("client") String client,
                                               @Query("udid") String udid);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 获取影评
     * @param id movie id
     */
    @GET("subject/{id}/reviews")
    Observable<MovieReviewBean> getMovieReviews(@Path("id") String id,
                                                @Query("apikey") String apikey,
                                                @Query("start") int start,
                                                @Query("count") int count,
                                                @Query("client") String client,
                                                @Query("udid") String udid);
    /**
     * 获取电影短评
     * @param id movie id
     */
    @GET("subject/{id}/comments")
    Observable<MovieCommentBean> getMovieComments(@Path("id") String id,
                                                  @Query("apikey") String apikey,
                                                  @Query("start") int start,
                                                  @Query("count") int count,
                                                  @Query("client") String client,
                                                  @Query("udid") String udid);
    /**
     * 获取剧照
     * @param id movie id
     */
    @GET("subject/{id}/photos")
    Observable<MoviePhotoBean> getMoviePhoto(@Path("id") String id,
                                             @Query("apikey") String apikey,
                                             @Query("start") int start,
                                             @Query("count") int count,
                                             @Query("client") String client,
                                             @Query("udid") String udid);
}
