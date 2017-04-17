package com.xiaolai.lapp.utils;

import android.Manifest;
import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseBooleanArray;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.callback.OnCompleteListener;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.welfare.db.LocalImageDb;
import com.xiaolai.lapp.module.welfare.model.LocalImageInfo;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class DownloadUtil {

    // 记录下载完的图片
    private static SparseBooleanArray sDlPhotos = new SparseBooleanArray();
    // 记录下载中的图片
    private static SparseBooleanArray sDoDlPhotos = new SparseBooleanArray();

    private DownloadUtil() {
        throw new RuntimeException("DownloadUtils cannot be initialized!");
    }

    /**
     * 先进行初始化，把之前下载的图片记录下来
     *
     */

    public static void init(){
        Observable.from(LocalImageDb.getInstance().getImageList(Constant.IMAGE_TYPE_DOWNLOAD))
                .compose(RxUtil.<LocalImageInfo>getBaseSchedulerTransform())
                .flatMap(new Func1<LocalImageInfo, Observable<String>>() {
                    @Override
                    public Observable<String> call(LocalImageInfo s) {
                        return Observable.just(s.getImageUrl());
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        sDlPhotos.put(s.hashCode(), true);
                    }
                });
    }

    public static void downloadImage(final String url, final OnCompleteListener listener){
        if(sDlPhotos.get(url.hashCode(),false)){
            ToastUtil.showToast("图片已存在");
            return;
        }
        if (sDoDlPhotos.get(url.hashCode(), false)) {
            ToastUtil.showToast("正在下载...");
            return;
        }
        sDoDlPhotos.put(url.hashCode(), true);
        ToastUtil.showToast("正在下载...");
        Observable.just(url)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        File file = null;
                        try {
                            file = Glide.with(LAppApplication.getContext()).load(url)
                                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        String path = Constant.IMAGE_DIR_PATH+System.currentTimeMillis()+".jpg";
                        // 复制图片文件到指定路径，并改为 .jpg 后缀名
                        if(FileUtil.copyFile(file.getPath(), path)){
                            LocalImageInfo imageInfo = new LocalImageInfo();
                            imageInfo.setType(Constant.IMAGE_TYPE_DOWNLOAD);
                            imageInfo.setImageUrl(url);
                            imageInfo.setImagePath(path);
                            imageInfo.setDesc(Constant.poetry.get((int)(0+Math.random()*(Constant.poetry.size()-1-0+1))));
                            imageInfo.setDate(TimeUtil.getDataFull());
                            imageInfo.save();
                            return path;
                        }
                        return "";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String path) {
                        if(!TextUtils.isEmpty(path)){
                            sDlPhotos.put(url.hashCode(), true);
                            if (listener != null) {
                                listener.onComplete(path);
                            }
                        }
                        sDoDlPhotos.put(url.hashCode(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (listener != null) {
                            listener.onFail();
                        }
                        sDoDlPhotos.put(url.hashCode(), false);
                    }
                });
    }

    public static boolean isPhotoDownloaded(String url) {
        return sDlPhotos.get(url.hashCode(), false);
    }
}
