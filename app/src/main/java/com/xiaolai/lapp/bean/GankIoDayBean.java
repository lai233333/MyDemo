package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class GankIoDayBean implements Serializable {

    private boolean error;
    private ResultsBean results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsBean {
        private List<GankItemBean> Android;
        private List<GankItemBean> iOS;
        private List<GankItemBean> front;
        private List<GankItemBean> app;
        private List<GankItemBean> restMovie;
        private List<GankItemBean> resource;
        private List<GankItemBean> recommend;

        public List<GankItemBean> getAndroid() {
            return Android;
        }

        public List<GankItemBean> getiOS() {
            return iOS;
        }

        public List<GankItemBean> getFront() {
            return front;
        }

        public List<GankItemBean> getApp() {
            return app;
        }

        public List<GankItemBean> getRestMovie() {
            return restMovie;
        }

        public List<GankItemBean> getResource() {
            return resource;
        }

        public List<GankItemBean> getRecommend() {
            return recommend;
        }

        public void setAndroid(List<GankItemBean> android) {
            Android = android;
        }

        public void setiOS(List<GankItemBean> iOS) {
            this.iOS = iOS;
        }

        public void setFront(List<GankItemBean> front) {
            this.front = front;
        }

        public void setApp(List<GankItemBean> app) {
            this.app = app;
        }

        public void setRestMovie(List<GankItemBean> restMovie) {
            this.restMovie = restMovie;
        }

        public void setResource(List<GankItemBean> resource) {
            this.resource = resource;
        }

        public void setRecommend(List<GankItemBean> recommend) {
            this.recommend = recommend;
        }
    }
}
