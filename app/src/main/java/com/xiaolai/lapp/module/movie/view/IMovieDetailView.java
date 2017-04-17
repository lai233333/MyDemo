package com.xiaolai.lapp.module.movie.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.MovieDetailBean;

/**
 * Created by laizhibin on 2017/3/28.
 */
public interface IMovieDetailView extends IBaseView {

    void loadData(MovieDetailBean data);
}
