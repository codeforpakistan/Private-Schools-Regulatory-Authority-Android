package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/12/2018.
 */


import com.squareup.moshi.Json;

import java.util.List;

public class DemoLogin {

    @Json(name = "success")
    private Integer success;
    @Json(name = "Message")
    private String message;
    @Json(name = "userInfoList")
    private List<UserInfoList> userInfoList = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserInfoList> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfoList> userInfoList) {
        this.userInfoList = userInfoList;
    }

    @Override
    public String toString() {
        return "DemoLogin{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", userInfoList=" + userInfoList +
                '}';
    }
}