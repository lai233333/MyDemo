package com.xiaolai.lapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Author xiaolai
 * @time 2017/4/3 1:32
 * @des 预告片剧照多条目实体
 */

public class TrailerPhotoEntity implements MultiItemEntity {
    private int itemType;
    private MovieTrailer movieTrailer;
    private MoviePhoto mMoviePhoto;


    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public MovieTrailer getMovieTrailer() {
        return movieTrailer;
    }

    public MoviePhoto getMoviePhoto() {
        return mMoviePhoto;
    }

    public void setMovieTrailer(MovieTrailer movieTrailer) {
        this.movieTrailer = movieTrailer;
    }

    public void setMoviePhoto(MoviePhoto moviePhoto) {
        mMoviePhoto = moviePhoto;
    }
}
