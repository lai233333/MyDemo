package com.xiaolai.lapp.module.movie.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.bean.MovieReviewBean;

/**
 * @Author xiaolai
 * @time 2017/4/3 18:41
 * @des 电影评论view
 */

public interface IMovieReviewView extends IBaseView {
    void loadData(MovieReviewBean data);
    void loadMoreData(MovieReviewBean data);
}
