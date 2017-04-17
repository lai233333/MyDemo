package com.xiaolai.lapp.module.movie.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.bean.MoviePhotoBean;
import com.xiaolai.lapp.module.movie.model.MovieModel;
import com.xiaolai.lapp.module.movie.view.IMoviePhotoView;

/**
 * @Author xiaolai
 * @time 2017/4/3 23:03
 * @des ${TODO}
 */

public class MoviePhotoPresenterImpl extends BasePresenterImpl<IMoviePhotoView,MoviePhotoBean> {

    private MovieModel mModel;
    private String id;
    private int start = 0;

    public MoviePhotoPresenterImpl(IMoviePhotoView view, String id) {
        super(view);
        mModel = new MovieModel();
        this.id = id;
    }

    @Override
    public void requestData() {
        super.requestData();
        start = 0;
        mSubscription = mModel.requestMoviePhotos(id,start,this);
    }

    @Override
    public void requestMoreData() {
        super.requestMoreData();
        mSubscription = mModel.requestMoviePhotos(id, start, this);
    }

    @Override
    public void requestSuccess(MoviePhotoBean data) {
        super.requestSuccess(data);
        start +=data.getCount();
        if(page == startPage){
            mView.loadData(data);
        }else{
            mView.loadMoreData(data);
        }
    }
}
