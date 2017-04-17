package com.xiaolai.lapp.common;

import android.os.Environment;

import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class Constant {

    public static final String SP_NAME = "config";
    public static final String UN_SELECTED = "110";
    public static final String SELECTED = "111";
    public static final String IMAGE_TYPE_COLLECT = "112";
    public static final String IMAGE_TYPE_DOWNLOAD = "113";
    public static final String APP_DIR_PATH =Environment.getExternalStorageDirectory()+ File.separator+"lapp" + File.separator;
    public static final String IMAGE_DIR_PATH =APP_DIR_PATH+"LPhotos"+File.separator;
    public static final String NET = "114";
    public static final String LOCAL = "115";
    public static final int MOVIE_TYPE_TOP = 111;
    public static final int MOVIE_TYPE_HOT = 112;
    public static final int ITEM_TYPE_TRAILER = 1111;
    public static final int ITEM_TYPE_PHOTO = 1112;
    public static List<String> poetry = Arrays.asList(LAppApplication.getContext().getResources().getStringArray(R.array.poetry));

    public static int getRandom(int max, int min){
        //(最小值+Math.random()*(最大值-最小值+1))
        return (int)(min+Math.random()*(max-min+1));
    }
}
