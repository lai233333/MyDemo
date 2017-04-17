package com.xiaolai.lapp.module.movie.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.MovieCommentBean;

/**
 * @Author xiaolai
 * @time 2017/4/3 18:41
 * @des 电影评论view
 */

public interface IMovieCommentView extends IBaseView {
    void loadData(MovieCommentBean data);
    void loadMoreData(MovieCommentBean data);
}
