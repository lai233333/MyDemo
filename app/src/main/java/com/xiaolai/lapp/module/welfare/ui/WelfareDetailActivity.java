package com.xiaolai.lapp.module.welfare.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.transition.Fade;
import android.widget.TextView;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.module.welfare.presenter.IWelfareDetailPresenter;
import com.xiaolai.lapp.module.welfare.presenter.WelfareDetailPresenterImpl;
import com.xiaolai.lapp.module.welfare.ui.adapter.WelfareDetailPageAdapter;
import com.xiaolai.lapp.module.welfare.view.IWelfareDetailView;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ActivityFinishEvent;
import com.xiaolai.lapp.utils.NavUtil;
import com.xiaolai.lapp.utils.ToastUtil;
import com.xiaolai.lapp.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareDetailActivity extends BaseActivity<IWelfareDetailPresenter> implements IWelfareDetailView {

    private List<String> imgList;
    private int currentPos;

    @BindView(R.id.vp_welfare)
    PhotoViewPager mViewPager;
    @BindView(R.id.tv_index)
    TextView tvIndex;

    private WelfareDetailPageAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_welfare_detail;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        currentPos = bundle.getInt("position");
        imgList = bundle.getStringArrayList("imgList");
        mPresenter = new WelfareDetailPresenterImpl(this);
        mAdapter = new WelfareDetailPageAdapter(this, imgList, mPresenter);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                tvIndex.setText(currentPos+1+" / " +imgList.size());
            }
        });
        registerActivityFinish();
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mViewPager.setCurrentItem(currentPos);
        tvIndex.setText(currentPos+1+" / " +imgList.size());
    }

    public static void launchActivity(Activity context, int position, ArrayList<String> imgList){
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putStringArrayList("imgList", imgList);
        Intent intent = new Intent(context, WelfareDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public void registerActivityFinish() {
        RxBus.getInstance().register(this, ActivityFinishEvent.class, new Action1<ActivityFinishEvent>() {
            @Override
            public void call(ActivityFinishEvent activityFinishEvent) {
                finish();
            }
        });
    }

    @Override
    public void downloadSuccess(String path) {
        if(!TextUtils.isEmpty(path)) {
            ToastUtil.showToast("图片已保存至：" + path);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        }else{
            ToastUtil.showToast("保存失败");
        }
    }

    @Override
    public void collectSuccess(boolean success) {
        if(success) {
            ToastUtil.showToast("收藏成功");
        }else{
            ToastUtil.showToast("收藏失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }
}
