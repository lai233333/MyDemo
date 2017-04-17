package com.xiaolai.lapp.module.movie.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.bean.MovieReviewBean;
import com.xiaolai.lapp.module.movie.model.MovieModel;
import com.xiaolai.lapp.module.movie.view.IMovieCommentView;
import com.xiaolai.lapp.module.movie.view.IMovieReviewView;

/**
 * @Author xiaolai
 * @time 2017/4/3 18:54
 * @des ${TODO}
 */

public class MovieReviewPresenterImpl extends BasePresenterImpl<IMovieReviewView,MovieReviewBean> {

    private MovieModel mModel;
    private String id;
    private int start = 0;

    public MovieReviewPresenterImpl(IMovieReviewView view, String id) {
        super(view);
        mModel = new MovieModel();
        this.id = id;
    }

    @Override
    public void requestData() {
        super.requestData();
        start = 0;
        mSubscription = mModel.requestMovieReviews(id, start, this);
    }

    @Override
    public void requestMoreData() {
        super.requestMoreData();
        mSubscription = mModel.requestMovieReviews(id, start, this);
    }

    @Override
    public void requestSuccess(MovieReviewBean data) {
        super.requestSuccess(data);
        this.start = data.getNext_start();
        if(page == startPage){
            mView.loadData(data);
        }else{
            mView.loadMoreData(data);
        }
    }
}
