package com.xiaolai.lapp.module.welfare.presenter;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.ImageShowBean;
import com.xiaolai.lapp.module.welfare.db.LocalImageDb;
import com.xiaolai.lapp.module.welfare.model.LocalImageInfo;
import com.xiaolai.lapp.module.welfare.view.IImageListView;
import com.xiaolai.lapp.utils.RxUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by laizhibin on 2017/3/24.
 */
public class ImageListPresenterImpl extends BasePresenterImpl<IImageListView, List<? extends ImageShowBean>> {

    String type;

    public ImageListPresenterImpl(IImageListView view, String type) {
        super(view);
        this.type = type;
    }

    @Override
    public void requestData() {
        mSubscription = Observable.create(new Observable.OnSubscribe<List<LocalImageInfo>>() {
            @Override
            public void call(Subscriber<? super List<LocalImageInfo>> subscriber) {
                subscriber.onNext(LocalImageDb.getInstance().getImageList(type));
                subscriber.onCompleted();
            }
        }).compose(RxUtil.<List<LocalImageInfo>>getBaseSchedulerTransform())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        beforeRequest();
                    }
                }).subscribe(new Subscriber<List<LocalImageInfo>>() {
                @Override
                public void onCompleted() {
                    requestComplete();
                }

                @Override
                public void onError(Throwable e) {
                    requestError(e.getLocalizedMessage());
                }

                @Override
                public void onNext(List<LocalImageInfo> localImageInfos) {
                    requestSuccess(localImageInfos);
                }
        });
    }

    public void deleteData(List<ImageShowBean> list, final String type){
        Observable.from(list).map(new Func1<ImageShowBean, Void>() {
            @Override
            public Void call(ImageShowBean imageShowBean) {
                LocalImageDb.getInstance().deleteImageInfoByUrl(((LocalImageInfo)imageShowBean).getImageUrl(),type);
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Logger.e("删除成功");
            }
        });
    }

    @Override
    public void requestSuccess(List<? extends ImageShowBean> data) {
        mView.loadData(data);
    }
}
