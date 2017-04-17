package com.xiaolai.lapp.module.movie.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.bean.TrailerPhotoEntity;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.utils.ImageLoadUtil;

import java.util.List;

/**
 * @Author xiaolai
 * @time 2017/4/3 1:50
 * @des ${TODO}
 */

public class TrailerPhotoAdapter extends BaseMultiItemQuickAdapter<TrailerPhotoEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TrailerPhotoAdapter(List<TrailerPhotoEntity> data) {
        super(data);
        addItemType(Constant.ITEM_TYPE_TRAILER, R.layout.item_trailer_list);
        addItemType(Constant.ITEM_TYPE_PHOTO,R.layout.item_photo_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrailerPhotoEntity item) {
        if(Constant.ITEM_TYPE_TRAILER == item.getItemType()){
            ImageLoadUtil.loadCenterCrop(item.getMovieTrailer().getMedium(), (ImageView) helper.getView(R.id.iv_movie_trailer));
        }else if(Constant.ITEM_TYPE_PHOTO == item.getItemType()){
            ImageLoadUtil.loadCenterCrop(item.getMoviePhoto().getCover(), (ImageView) helper.getView(R.id.iv_movie_photo));
        }
    }
}
