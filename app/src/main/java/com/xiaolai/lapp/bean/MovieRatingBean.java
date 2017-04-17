package com.xiaolai.lapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class MovieRatingBean implements Serializable{

    private int max;
    private double average;
    private double value;
    private String stars;
    private int min;

    private RatingDetail details;

    public void setDetails(RatingDetail details) {
        this.details = details;
    }

    public RatingDetail getDetails() {
        return details;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static class RatingDetail{
        @SerializedName("1")
        private int star1;
        @SerializedName("2")
        private int star2;
        @SerializedName("3")
        private int star3;
        @SerializedName("4")
        private int star4;
        @SerializedName("5")
        private int star5;

        public int getStar1() {
            return star1;
        }

        public int getStar2() {
            return star2;
        }

        public int getStar3() {
            return star3;
        }

        public int getStar4() {
            return star4;
        }

        public int getStar5() {
            return star5;
        }

        public void setStar1(int star1) {
            this.star1 = star1;
        }

        public void setStar2(int star2) {
            this.star2 = star2;
        }

        public void setStar3(int star3) {
            this.star3 = star3;
        }

        public void setStar4(int star4) {
            this.star4 = star4;
        }

        public void setStar5(int star5) {
            this.star5 = star5;
        }
    }

    public int getMax() {
        return max;
    }

    public double getAverage() {
        return average;
    }

    public String getStars() {
        return stars;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
