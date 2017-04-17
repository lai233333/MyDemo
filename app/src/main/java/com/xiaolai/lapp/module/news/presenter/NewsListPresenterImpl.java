package com.xiaolai.lapp.module.news.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.module.news.model.INewsListModel;
import com.xiaolai.lapp.module.news.model.NewsListModelImpl;
import com.xiaolai.lapp.module.news.view.INewsListView;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/22.
 */
public class NewsListPresenterImpl extends BasePresenterImpl<INewsListView,List<GankItemBean>> implements INewsListPresenter{

    private String gankTypeValue;
    private INewsListModel newsListModel;

    public NewsListPresenterImpl(INewsListView view, String gankTypeValue) {
        super(view);
        this.gankTypeValue = gankTypeValue;
        newsListModel = new NewsListModelImpl();
    }

    @Override
    public void requestData() {
        super.requestData();
        mSubscription = newsListModel.requestData(gankTypeValue, page, this);
    }

    @Override
    public void requestAdData() {
        mSubscription = newsListModel.requestAdData();
    }

    @Override
    public void loadAdData() {

    }

    @Override
    public void requestMoreData() {
        super.requestMoreData();
        newsListModel.requestData(gankTypeValue, page, this);
    }

    @Override
    public void requestSuccess(List<GankItemBean> data) {
        if(page == 1) {
            mView.loadData(data);
        }else{
            mView.loadMoreData(data);
        }
    }

}
