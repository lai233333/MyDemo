package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class GankIoDataBean implements Serializable{

    private boolean error;

    private List<GankItemBean> results;

    public boolean isError() {
        return error;
    }

    public List<GankItemBean> getResults() {
        return results;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<GankItemBean> results) {
        this.results = results;
    }

    /*public static class ResultBean implements Serializable {
        *//**
         * _id : 5832662b421aa929b0f34e99
         * createdAt : 2016-11-21T11:12:43.567Z
         * desc :  深入Android渲染机制
         * publishedAt : 2016-11-24T11:40:53.615Z
         * source : web
         * type : Android
         * url : http://blog.csdn.net/ccj659/article/details/53219288
         * used : true
         * who : Chauncey
         *//*

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getSource() {
            return source;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean isUsed() {
            return used;
        }

        public String getWho() {
            return who;
        }

        public List<String> getImages() {
            return images;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }*/
}
