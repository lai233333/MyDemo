package com.xiaolai.lapp.module.welfare.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cocosw.bottomsheet.BottomSheet;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.module.welfare.presenter.IWelfareDetailPresenter;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ActivityFinishEvent;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.utils.ToastUtil;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareDetailPageAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener{

    private Context mContext;
    private List<String> imgList;
    private IWelfareDetailPresenter mPresenter;

    public WelfareDetailPageAdapter(Context mContext, List<String> imgList, IWelfareDetailPresenter mPresenter){
        this.mContext = mContext;
        this.imgList = imgList;
        this.mPresenter = mPresenter;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(mContext, R.layout.item_welfare_detail,null);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.pv_welfare);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_load);
        container.addView(view);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        final RequestListener requestListener = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                ToastUtil.showToast("图片加载异常");
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        };
        ImageLoadUtil.loadCenterCrop(getItem(position),photoView,requestListener);
        photoView.setOnPhotoTapListener(this);
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new BottomSheet.Builder(mContext, R.style.BottomSheet_StyleDialog)
                        .sheet(R.menu.menu_image).listener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_collection:
                                mPresenter.collectImage(getItem(position));
                                break;
                            case R.id.item_download:
                                mPresenter.downloadImage(getItem(position));
                                break;
                            case R.id.item_share:
                                mPresenter.shareImage(mContext, getItem(position));
                                break;
                            default:
                                ToastUtil.showToast("你点个what");
                                break;
                        }
                        return false;
                    }
                }).show();
                return false;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public String getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        RxBus.getInstance().post(new ActivityFinishEvent());
    }

}
