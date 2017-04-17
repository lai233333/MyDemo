package com.xiaolai.lapp.module.movie.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.bean.MovieDetailBean;
import com.xiaolai.lapp.module.movie.model.IRequestDataModel;
import com.xiaolai.lapp.module.movie.model.MovieModel;
import com.xiaolai.lapp.module.movie.view.IMovieDetailView;

/**
 * Created by laizhibin on 2017/3/28.
 */
public class MovieDetailPresenterImpl extends BasePresenterImpl<IMovieDetailView, MovieDetailBean> {

    IRequestDataModel mModel;
    String id;

    public MovieDetailPresenterImpl(IMovieDetailView view, String id) {
        super(view);
        this.id = id;
        mModel = new MovieModel();
    }

    @Override
    public void requestData() {
        mSubscription = mModel.requestMovieDetail(id, this);
    }

    @Override
    public void requestSuccess(MovieDetailBean data) {
        mView.loadData(data);
    }
}
