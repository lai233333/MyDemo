package com.xiaolai.lapp;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.http.RetrofitHttpClient;
import com.xiaolai.lapp.utils.DownloadUtil;
import com.xiaolai.lapp.utils.ToastUtil;

import org.litepal.LitePal;

/**
 * Created by laizhibin on 2017/3/17.
 */
public class LAppApplication extends Application{

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;

        RetrofitHttpClient.init();
        ToastUtil.init(mApplicationContext);
        Logger.init("Lai");
        LitePal.initialize(this);
        DownloadUtil.init();
    }

    public static Context getContext(){
        return mApplicationContext;
    }
}
