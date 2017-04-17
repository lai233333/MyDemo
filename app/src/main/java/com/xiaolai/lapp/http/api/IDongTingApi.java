package com.xiaolai.lapp.http.api;

import com.xiaolai.lapp.bean.FrontPageBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by laizhibin on 2017/3/21.
 */
public interface IDongTingApi {
    /**
     * 首页轮播图
     */
    @GET("frontpage/frontpage")
    Observable<FrontPageBean> getFrontpage();
}
