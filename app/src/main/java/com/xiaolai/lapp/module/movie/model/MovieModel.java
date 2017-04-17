package com.xiaolai.lapp.module.movie.model;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.bean.HotMovieBean;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.bean.MovieDetailBean;
import com.xiaolai.lapp.bean.MovieItemBean;
import com.xiaolai.lapp.bean.MoviePhotoBean;
import com.xiaolai.lapp.bean.MovieReviewBean;
import com.xiaolai.lapp.callback.RequestCallBack;
import com.xiaolai.lapp.http.RetrofitHttpClient;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by laizhibin on 2017/3/27.
 */
public class MovieModel implements IRequestDataModel {
    @Override
    public Subscription requestHotMovie(int start, final RequestCallBack callBack) {
        return RetrofitHttpClient.getHotMovie(start)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).flatMap(new Func1<HotMovieBean, Observable<List<MovieItemBean>>>() {
                    @Override
                    public Observable<List<MovieItemBean>> call(HotMovieBean hotMovieBean) {
                        return Observable.just(hotMovieBean.getSubjects());
                    }
                }).subscribe(new Observer<List<MovieItemBean>>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<MovieItemBean> data) {
                        callBack.requestSuccess(data);
                    }
                });
    }

    @Override
    public Subscription requestTopMovie(int start, final RequestCallBack callBack) {
        return RetrofitHttpClient.getMovieTop250(start)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Observer<List<MovieItemBean>>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<MovieItemBean> data) {
                        callBack.requestSuccess(data);
                    }
                });
    }

    @Override
    public Subscription requestMovieDetail(String id, final RequestCallBack callBack) {
        return RetrofitHttpClient.getMovieDetail(id)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Observer<MovieDetailBean>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(MovieDetailBean data) {
                        callBack.requestSuccess(data);
                    }
                });
    }

    @Override
    public Subscription requestMovieComments(String id, int start, final RequestCallBack callBack) {
        return RetrofitHttpClient.getMovieComments(id,start)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Observer<MovieCommentBean>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(MovieCommentBean data) {
                        callBack.requestSuccess(data);
                    }
                });
    }

    @Override
    public Subscription requestMovieReviews(String id, int start, final RequestCallBack callBack) {
        return RetrofitHttpClient.getMovieReviews(id,start)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Observer<MovieReviewBean>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(MovieReviewBean data) {
                        callBack.requestSuccess(data);
                    }
                });
    }

    @Override
    public Subscription requestMoviePhotos(String id, int start, final RequestCallBack callBack) {
        return RetrofitHttpClient.getMoviePhoto(id,start)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Observer<MoviePhotoBean>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(MoviePhotoBean data) {
                        callBack.requestSuccess(data);
                    }
                });
    }
}
