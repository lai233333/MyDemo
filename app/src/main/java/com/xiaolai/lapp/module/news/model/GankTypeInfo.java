package com.xiaolai.lapp.module.news.model;

import org.litepal.crud.DataSupport;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class GankTypeInfo extends DataSupport {

    private String key;
    private String value;
    private String isSelected;
    private int order;

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getIsSelected() {

        return isSelected;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
