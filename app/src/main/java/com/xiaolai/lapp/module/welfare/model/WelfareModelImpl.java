package com.xiaolai.lapp.module.welfare.model;

import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.callback.RequestCallBack;
import com.xiaolai.lapp.http.RetrofitHttpClient;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareModelImpl implements IWelfareModel {
    @Override
    public Subscription requestData(String id, int page, final RequestCallBack<List<GankItemBean>> callBack) {
        return RetrofitHttpClient.getGankIoData(id, page)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribe(new Observer<List<GankItemBean>>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        callBack.requestSuccess(gankItemBeen);
                    }
                });
    }
}
