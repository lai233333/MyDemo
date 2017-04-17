package com.xiaolai.lapp.rxbus.event;

/**
 * Created by laizhibin on 2017/3/26.
 */
public class ImageManagerEvent {
    public static final int SHOW_MENU_EVENT = 1111;
    public static final int ALL_SELECT_EVENT = 1112;
    public static final int SWAP_EVENT = 303;

    private int eventType;
    private boolean allSelect;

    public ImageManagerEvent(int eventType) {
        this.eventType = eventType;
    }

    public ImageManagerEvent(int eventType, boolean allSelect) {
        this.eventType = eventType;
        this.allSelect = allSelect;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public boolean isAllSelect() {
        return allSelect;
    }

    public void setAllSelect(boolean allSelect) {
        this.allSelect = allSelect;
    }
}
