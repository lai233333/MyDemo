package com.xiaolai.lapp.callback;

/**
 * Created by laizhibin on 2017/3/24.
 */
public interface OnCompleteListener {

    void onComplete(String path);

    void onFail();

    void onDelete(String path);
}
