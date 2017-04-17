package com.xiaolai.lapp.module.movie.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.MoviePhotoBean;

/**
 * @Author xiaolai
 * @time 2017/4/3 23:04
 * @des ${TODO}
 */

public interface IMoviePhotoView extends IBaseView {

    void loadData(MoviePhotoBean data);
    void loadMoreData(MoviePhotoBean data);
}
