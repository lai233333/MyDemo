package com.xiaolai.lapp.module.welfare.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.callback.SimpleOnCompleteListener;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.welfare.db.LocalImageDb;
import com.xiaolai.lapp.module.welfare.model.IWelfareDetailModel;
import com.xiaolai.lapp.module.welfare.model.WelfareDetailModelImpl;
import com.xiaolai.lapp.module.welfare.view.IWelfareDetailView;
import com.xiaolai.lapp.utils.ShareUtil;
import com.xiaolai.lapp.utils.ToastUtil;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareDetailPresenterImpl extends BasePresenterImpl<IWelfareDetailView,Object> implements IWelfareDetailPresenter {

    IWelfareDetailModel mModel;

    public WelfareDetailPresenterImpl(IWelfareDetailView view) {
        super(view);
        mModel = new WelfareDetailModelImpl();
    }

    @Override
    public void shareImage(Context context, String url) {
        ShareUtil.share(context, LAppApplication.getContext().getResources().getString(R.string.share_image));
    }

    @Override
    public void downloadImage(String url) {
        mModel.downloadImage(url, new SimpleOnCompleteListener(){
            @Override
            public void onComplete(String path) {
                mView.downloadSuccess(path);
            }

            @Override
            public void onFail() {
                mView.downloadSuccess("");
            }
        });
    }

    @Override
    public void collectImage(String url) {
        if(LocalImageDb.getInstance().getImageInfoByUrl(url, Constant.IMAGE_TYPE_COLLECT) != null){
            ToastUtil.showToast("不要重复收藏啦");
            return;
        }
        mView.collectSuccess(mModel.collectImage(url));
    }
}
