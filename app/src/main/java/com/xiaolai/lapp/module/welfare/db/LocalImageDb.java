package com.xiaolai.lapp.module.welfare.db;

import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.welfare.model.LocalImageInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by laizhibin on 2017/3/24.
 */
public class LocalImageDb {

    private static LocalImageDb sInstance;

    private LocalImageDb(){}

    public static LocalImageDb getInstance(){
        if(sInstance == null){
            synchronized (LocalImageDb.class){
                if(sInstance == null){
                    sInstance = new LocalImageDb();
                }
            }
        }
        return sInstance;
    }

    public List<LocalImageInfo> getImageList(String type){
        return DataSupport.where("type=?", type).find(LocalImageInfo.class);
    }

    public LocalImageInfo getImageInfoByUrl(String url, String type){
        return DataSupport.where("imageUrl=? and type=?",url,type).findFirst(LocalImageInfo.class);
    }

    public void deleteImageInfoByUrl(String url, String type){
        DataSupport.deleteAll(LocalImageInfo.class,"imageUrl=? and type=?",url,type);
    }

}
