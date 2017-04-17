package com.xiaolai.lapp.module.news.model;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.callback.OnRetryListener;
import com.xiaolai.lapp.callback.RequestCallBack;
import com.xiaolai.lapp.http.RetrofitHttpClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by laizhibin on 2017/3/22.
 */
public class NewsListModelImpl implements INewsListModel {

    @Override
    public Subscription requestData(String id, int page, final RequestCallBack<List<GankItemBean>> callBack) {
        return RetrofitHttpClient.getGankIoData(id, page)
                .map(new Func1<List<GankItemBean>, List<GankItemBean>>() {
                    @Override
                    public List<GankItemBean> call(List<GankItemBean> gankItemBeen) {
                        List<GankItemBean> list = new ArrayList<GankItemBean>();
                        for(GankItemBean bean : gankItemBeen){
                            if(!"休息视频".equals(bean.getType())){
                                list.add(bean);
                            }
                        }
                        return list;
                    }
                })
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
                        Logger.e(e.getLocalizedMessage());
                        callBack.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        callBack.requestSuccess(gankItemBeen);
                    }
                });
    }

    @Override
    public Subscription requestAdData() {
        return null;
    }
}
