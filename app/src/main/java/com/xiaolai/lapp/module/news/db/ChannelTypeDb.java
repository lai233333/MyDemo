package com.xiaolai.lapp.module.news.db;

import android.content.ContentValues;

import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/22.
 */
public class ChannelTypeDb {

    private static ChannelTypeDb sInstance;

    private ChannelTypeDb(){}

    public static ChannelTypeDb getInstance(){
        if(sInstance == null){
            synchronized (ChannelTypeDb.class){
                if(sInstance == null){
                    sInstance = new ChannelTypeDb();
                }
            }
        }
        return sInstance;
    }

    public void initData(){
        List<String> channelKeys = Arrays.asList(LAppApplication.getContext().getResources()
                .getStringArray(R.array.news_channel));
        List<String> channelValues = Arrays.asList(LAppApplication.getContext().getResources()
                .getStringArray(R.array.news_channel_value));
        for(int i=0; i<channelKeys.size(); i++){
            GankTypeInfo gankTypeInfo = new GankTypeInfo();
            gankTypeInfo.setKey(channelKeys.get(i));
            gankTypeInfo.setValue(channelValues.get(i));
            gankTypeInfo.setIsSelected(i<3?Constant.SELECTED:Constant.UN_SELECTED);
            gankTypeInfo.setOrder(i);
            gankTypeInfo.save();
        }
    }

    public List<GankTypeInfo> getSelectChannelList(){
        return DataSupport.where("isSelected=?",Constant.SELECTED).order("order").find(GankTypeInfo.class);
    }

    public List<GankTypeInfo> getUnSelectChannelList(){
        return DataSupport.where("isSelected=?",Constant.UN_SELECTED).order("order").find(GankTypeInfo.class);
    }

    public int updateState(String key, String select){
        ContentValues values = new ContentValues();
        values.put("isSelected", select);
        return DataSupport.updateAll(GankTypeInfo.class,values,"key = ?",key);
    }

    public void moveToLast(String key){
        GankTypeInfo last = DataSupport.order("order").findLast(GankTypeInfo.class);
        ContentValues values = new ContentValues();
        values.put("order", last.getOrder()+1);
        DataSupport.updateAll(GankTypeInfo.class,values,"key = ?",key);
    }

    public void swapItem(String fromKey, String toKey, int fromPos, int toPos){
        GankTypeInfo from = DataSupport.where("key=?", fromKey).findFirst(GankTypeInfo.class);
        GankTypeInfo to = DataSupport.where("key=?", toKey).findFirst(GankTypeInfo.class);
        int fromOrder = from.getOrder();
        int toOrder = to.getOrder();
        if(Math.abs(fromPos - toPos) == 1) {
            from.setOrder(toOrder);
            from.save();
            to.setOrder(fromOrder);
            to.save();
        }else if(fromPos - toPos > 0){
            //开始的位置大于要去的位置
            List<GankTypeInfo> list = DataSupport.where("order>=? and order<?", String.valueOf(toPos),String.valueOf(fromPos)).find(GankTypeInfo.class);
            for(GankTypeInfo info: list){
                info.setOrder(info.getOrder()+1);
                info.save();
            }
            from.setOrder(toOrder);
            from.save();
        }else if(fromPos - toPos < 0){
            //开始的位置小于要去的位置
            List<GankTypeInfo> list = DataSupport.where("order>=? and order<?", String.valueOf(fromPos),String.valueOf(toPos)).find(GankTypeInfo.class);
            for(GankTypeInfo info: list){
                info.setOrder(info.getOrder()-1);
                info.save();
            }
            from.setOrder(toOrder);
            from.save();
        }
    }
}
