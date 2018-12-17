package com.psra.complaintsystem.modle;

/**
 * Created by HP on 11/7/2018.
 */

import com.squareup.moshi.Json;

public class NotifModule {

    @Json(name = "to")
    private String to;
    @Json(name = "msg")
    private String msg;
    @Json(name = "title")
    private String title;
    @Json(name = "data")
    private Data data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NotifModule{" +
                "to='" + to + '\'' +
                ", msg='" + msg + '\'' +
                ", title='" + title + '\'' +
                ", data=" + data +
                '}';
    }
}
