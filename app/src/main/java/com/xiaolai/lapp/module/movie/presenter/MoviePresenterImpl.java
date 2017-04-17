package com.xiaolai.lapp.module.movie.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.bean.MovieItemBean;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.movie.model.IRequestDataModel;
import com.xiaolai.lapp.module.movie.model.MovieModel;
import com.xiaolai.lapp.module.movie.view.IMovieView;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/27.
 */
public class MoviePresenterImpl extends BasePresenterImpl<IMovieView, List<MovieItemBean>> {

    IRequestDataModel mModel;
    int start;

    public MoviePresenterImpl(IMovieView view,int start) {
        super(view);
        mModel = new MovieModel();
        this.start = start;
        this.startPage = 0;
    }

    @Override
    public void requestData() {
        super.requestData();
        if(mView.getType() == Constant.MOVIE_TYPE_HOT) {
            mSubscription = mModel.requestHotMovie(page,this);
        }else if(mView.getType() == Constant.MOVIE_TYPE_TOP){
            mSubscription = mModel.requestTopMovie(start, this);
        }
    }

    @Override
    public void requestMoreData() {
        super.requestMoreData();
        mSubscription = mModel.requestHotMovie(page,this);
    }

    @Override
    public void requestSuccess(List<MovieItemBean> data) {
        if(page==startPage) {
            mView.loadData(data);
        }else{
            mView.loadMoreData(data);
        }
    }
}
