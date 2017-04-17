package com.xiaolai.lapp.module.news.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.module.news.model.ChannelModelImpl;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;
import com.xiaolai.lapp.module.news.model.IChannelModel;
import com.xiaolai.lapp.module.news.view.IChannelView;

import java.util.List;
import java.util.Map;

/**
 * Created by laizhibin on 2017/3/26.
 */
public class ChannelPresenterImpl extends BasePresenterImpl<IChannelView, Map<Boolean, List<GankTypeInfo>>> implements IChannelPresenter {

    IChannelModel mModel;

    public ChannelPresenterImpl(IChannelView view) {
        super(view);
        mModel = new ChannelModelImpl();
    }

    @Override
    public void requestData() {
        mSubscription = mModel.requestData(this);
    }

    @Override
    public void requestSuccess(Map<Boolean, List<GankTypeInfo>> data) {
        mView.loadData(data.get(true), data.get(false));
    }

    @Override
    public void onChannelAddOrRemove(String typeKey, boolean select) {
        mSubscription = mModel.updateData(typeKey, select);
    }

    @Override
    public void onChannelSwap(String fromKey, String toKey, int fromPos, int toPos) {
        mSubscription = mModel.swapData(fromKey, toKey, fromPos, toPos);
    }
}
