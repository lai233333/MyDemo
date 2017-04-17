package com.xiaolai.lapp.module.news.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.utils.TimeUtil;
import com.xiaolai.lapp.utils.ToastUtil;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/22.
 */
public class GankListAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

    private boolean isAll = false;

    public void setAllType(boolean isAll) {
        this.isAll = isAll;
    }

    public GankListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankItemBean item) {
        if (isAll && "福利".equals(item.getType())) {
            helper.getView(R.id.iv_type_welfare).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_type_other).setVisibility(View.GONE);
            ImageLoadUtil.loadCenterCrop(item.getUrl(), (ImageView) helper.getView(R.id.iv_type_welfare));
        } else {
            helper.getView(R.id.iv_type_welfare).setVisibility(View.GONE);
            helper.getView(R.id.ll_type_other).setVisibility(View.VISIBLE);
        }
        if (isAll) {
            helper.getView(R.id.tv_gank_type).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_gank_type, " · " + item.getType());
        } else {
            helper.getView(R.id.tv_gank_type).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_gank_des, item.getDesc());
        if (item.getImages() != null
                && item.getImages().size() > 0
                && !TextUtils.isEmpty(item.getImages().get(0))) {
            helper.getView(R.id.iv_gank_pic).setVisibility(View.VISIBLE);
            ImageLoadUtil.displayGif(item.getImages().get(0), (ImageView) helper.getView(R.id.iv_gank_pic));
        } else {
            helper.getView(R.id.iv_gank_pic).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_gank_who, TextUtils.isEmpty(item.getWho())?"小赖":item.getWho());
        helper.setText(R.id.tv_gank_time, TimeUtil.getTranslateTime(item.getPublishedAt()));
    }

}
