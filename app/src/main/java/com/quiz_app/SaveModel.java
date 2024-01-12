package com.quiz_app;

public class SaveModel {
    String score,date_time,key,total;

    public SaveModel(String score, String date_time, String key, String total) {
        this.score = score;
        this.date_time = date_time;
        this.key = key;
        this.total = total;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
