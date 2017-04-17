package com.xiaolai.lapp.module.movie.presenter;

import com.xiaolai.lapp.base.BasePresenterImpl;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.module.movie.model.MovieModel;
import com.xiaolai.lapp.module.movie.view.IMovieCommentView;

/**
 * @Author xiaolai
 * @time 2017/4/3 18:54
 * @des ${TODO}
 */

public class MovieCommentPresenterImpl extends BasePresenterImpl<IMovieCommentView,MovieCommentBean> {

    private MovieModel mModel;
    private String id;
    private int start = 0;

    public MovieCommentPresenterImpl(IMovieCommentView view, String id) {
        super(view);
        mModel = new MovieModel();
        startPage = 0;
        this.id = id;
    }

    @Override
    public void requestData() {
        super.requestData();
        start = 0;
        mSubscription = mModel.requestMovieComments(id, start, this);
    }

    @Override
    public void requestMoreData() {
        super.requestMoreData();
        mSubscription = mModel.requestMovieComments(id, start, this);
    }

    @Override
    public void requestSuccess(MovieCommentBean data) {
        super.requestSuccess(data);
        start = data.getNext_start();
        if(page == startPage){
            mView.loadData(data);
        }else{
            mView.loadMoreData(data);
        }
    }
}
