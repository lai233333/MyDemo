package com.xiaolai.lapp.rxbus.event;

import com.xiaolai.lapp.module.news.model.GankTypeInfo;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class ChannelEvent {

    public static final int ADD_EVENT = 301;
    public static final int DEL_EVENT = 302;
    public static final int SWAP_EVENT = 303;

    private int eventType;
    private GankTypeInfo gankTypeInfo;
    private int fromPos = -1;
    private int toPos = -1;

    public ChannelEvent(int eventType, GankTypeInfo gankTypeInfo) {
        this.eventType = eventType;
        this.gankTypeInfo = gankTypeInfo;
    }

    public ChannelEvent(int eventType, int fromPos, int toPos) {
        this.eventType = eventType;
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public GankTypeInfo getGankTypeInfo() {
        return gankTypeInfo;
    }

    public int getEventType() {
        return eventType;
    }

    public int getFromPos() {
        return fromPos;
    }

    public int getToPos() {
        return toPos;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setGankTypeInfo(GankTypeInfo gankTypeInfo) {
        this.gankTypeInfo = gankTypeInfo;
    }

    public void setFromPos(int fromPos) {
        this.fromPos = fromPos;
    }

    public void setToPos(int toPos) {
        this.toPos = toPos;
    }
}
