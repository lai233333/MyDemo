package com.xiaolai.lapp.module.welfare.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.module.welfare.model.IWelfareModel;
import com.xiaolai.lapp.module.welfare.model.WelfareModelImpl;
import com.xiaolai.lapp.module.welfare.view.IWelfareView;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfarePresenterImpl extends BasePresenterImpl<IWelfareView, List<GankItemBean>> implements IWelfarePresenter {

    private String gankTypeValue;
    private IWelfareModel welfareModel;

    public WelfarePresenterImpl(IWelfareView view, String gankTypeValue) {
        super(view);
        this.gankTypeValue = gankTypeValue;
        welfareModel = new WelfareModelImpl();
    }

    @Override
    public void requestData() {
        super.requestData();
        welfareModel.requestData(gankTypeValue, page, this);
    }

    @Override
    public void requestMoreData() {
        super.requestMoreData();
        welfareModel.requestData(gankTypeValue, page, this);
    }

    @Override
    public void requestSuccess(List<GankItemBean> data) {
        super.requestSuccess(data);
        if(page == 1) {
            mView.loadData(data);
        }else{
            mView.loadMoreData(data);
        }
    }
}
