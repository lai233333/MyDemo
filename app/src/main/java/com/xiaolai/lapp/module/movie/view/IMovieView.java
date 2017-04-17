package com.xiaolai.lapp.module.movie.view;

import com.xiaolai.lapp.base.IBaseView;
import com.xiaolai.lapp.bean.MovieItemBean;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/27.
 */
public interface IMovieView extends IBaseView {
    void loadData(List<MovieItemBean> data);
    void loadMoreData(List<MovieItemBean> data);
    int getType();
}
