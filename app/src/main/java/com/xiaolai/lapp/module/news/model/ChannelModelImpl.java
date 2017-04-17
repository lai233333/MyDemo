package com.xiaolai.lapp.module.news.model;

import com.xiaolai.lapp.callback.RequestCallBack;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.news.db.ChannelTypeDb;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by laizhibin on 2017/3/26.
 */
public class ChannelModelImpl implements IChannelModel {
    @Override
    public Subscription requestData(final RequestCallBack<Map<Boolean, List<GankTypeInfo>>> callBack) {
        return Observable.create(new Observable.OnSubscribe<Map<Boolean, List<GankTypeInfo>>>() {
            @Override
            public void call(Subscriber<? super Map<Boolean, List<GankTypeInfo>>> subscriber) {
                HashMap<Boolean, List<GankTypeInfo>> map = new HashMap<>();
                map.put(true, ChannelTypeDb.getInstance().getSelectChannelList());
                map.put(false, ChannelTypeDb.getInstance().getUnSelectChannelList());
                subscriber.onNext(map);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<Boolean, List<GankTypeInfo>>>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.requestError(
                                e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(Map<Boolean, List<GankTypeInfo>> data) {
                        callBack.requestSuccess(data);
                    }
                });
    }

    @Override
    public Subscription updateData(final String typeKey, final boolean select) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                String selectStr = "";
                if(select){
                    selectStr = Constant.SELECTED;
                }else{
                    selectStr = Constant.UN_SELECTED;
                }
                //将此数据移动到表的最后
                ChannelTypeDb.getInstance().moveToLast(typeKey);
                ChannelTypeDb.getInstance().updateState(typeKey, selectStr);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                    }
                });
    }

    @Override
    public Subscription swapData(final String fromKey, final String toKey, final int fromPos, final int toPos) {
        return Observable.create(new Observable.OnSubscribe<Object>() {

            @Override
            public void call(Subscriber<? super Object> subscriber) {
                ChannelTypeDb.getInstance().swapItem(fromKey, toKey, fromPos, toPos);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
