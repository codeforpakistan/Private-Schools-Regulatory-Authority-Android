package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/16/2018.
 */

import com.squareup.moshi.Json;


public class Registered {

    @Json(name = "userId")
    private Integer userId;
    @Json(name = "success")
    private Integer success;
    @Json(name = "Message")
    private String message;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return "Registered{" +
                "userId=" + userId +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
