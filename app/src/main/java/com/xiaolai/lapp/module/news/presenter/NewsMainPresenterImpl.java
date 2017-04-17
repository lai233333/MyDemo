package com.xiaolai.lapp.module.news.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;
import com.xiaolai.lapp.module.news.model.INewsMainModel;
import com.xiaolai.lapp.module.news.model.NewsMainModelImpl;
import com.xiaolai.lapp.module.news.view.INewsMainView;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/19.
 */
public class NewsMainPresenterImpl extends BasePresenterImpl<INewsMainView, List<GankTypeInfo>> implements INewsMainPresenter {
    private INewsMainModel newsMainModel;

    public NewsMainPresenterImpl(INewsMainView view) {
        super(view);
        newsMainModel = new NewsMainModelImpl();
        mView.registerRxBusEvent();
    }

    @Override
    public void requestChannelDb() {
        mSubscription = newsMainModel.getChannelList(this);
    }

    @Override
    public void requestSuccess(List<GankTypeInfo> data) {
        mView.loadData(data);
    }
}
