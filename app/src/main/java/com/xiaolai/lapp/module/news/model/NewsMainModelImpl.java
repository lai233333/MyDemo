package com.xiaolai.lapp.module.news.model;

import com.xiaolai.lapp.callback.RequestCallBack;
import com.xiaolai.lapp.module.news.db.ChannelTypeDb;
import com.xiaolai.lapp.utils.SpUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class NewsMainModelImpl implements INewsMainModel {
    @Override
    public Subscription getChannelList(final RequestCallBack<List<GankTypeInfo>> callBack) {
        return Observable.create(new Observable.OnSubscribe<List<GankTypeInfo>>(){

            @Override
            public void call(Subscriber<? super List<GankTypeInfo>> subscriber) {
                if (!SpUtil.readBoolean("initDb")) {
                    ChannelTypeDb.getInstance().initData();
                    SpUtil.writeBoolean("initDb", true);
                }
                subscriber.onNext(ChannelTypeDb.getInstance().getSelectChannelList());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Subscriber<List<GankTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<GankTypeInfo> gankTypeInfos) {
                        callBack.requestSuccess(gankTypeInfos);
                    }
                });
    }
}
