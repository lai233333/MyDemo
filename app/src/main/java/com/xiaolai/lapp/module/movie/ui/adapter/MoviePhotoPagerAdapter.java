package com.xiaolai.lapp.module.movie.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.bean.MoviePhoto;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ViewShowEvent;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @Author xiaolai
 * @time 2017/4/3 22:39
 * @des ${TODO}
 */

public class MoviePhotoPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<MoviePhoto> mList;

    public MoviePhotoPagerAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(mContext, R.layout.item_movie_photo,null);
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
        ImageLoadUtil.loadCenterCrop(mList.get(position).getImage(),photoView,requestListener);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                RxBus.getInstance().post(new ViewShowEvent());
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public void setNewData(List<MoviePhoto> data){
        this.mList = data;
        notifyDataSetChanged();
    }

    public void addData(List<MoviePhoto> data){
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public MoviePhoto getItem(int position){
        return mList.get(position);
    }
}
