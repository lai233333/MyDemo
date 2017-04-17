package com.xiaolai.lapp.module.news.presenter;

import com.xiaolai.lapp.base.IBasePresenter;

/**
 * Created by laizhibin on 2017/3/26.
 */
public interface IChannelPresenter extends IBasePresenter{

    void onChannelAddOrRemove(String typeKey, boolean select);

    void onChannelSwap(String fromKey, String toKey, int fromPos, int toPos);
}
