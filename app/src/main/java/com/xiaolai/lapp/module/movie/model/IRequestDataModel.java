package com.xiaolai.lapp.module.movie.model;

import com.xiaolai.lapp.callback.RequestCallBack;

import rx.Subscription;

/**
 * Created by laizhibin on 2017/3/27.
 */
public interface IRequestDataModel {
    Subscription requestHotMovie(int start,RequestCallBack callBack);
    Subscription requestTopMovie(int start,RequestCallBack callBack);
    Subscription requestMovieDetail(String id, RequestCallBack callBack);
    Subscription requestMovieComments(String id, int start,RequestCallBack callBack);
    Subscription requestMovieReviews(String id, int start,RequestCallBack callBack);
    Subscription requestMoviePhotos(String id, int start,RequestCallBack callBack);
}
