package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/23/2018.
 */

import com.squareup.moshi.Json;

public class ComplainModle {

    @Json(name = "success")
    private Integer success;
    @Json(name = "Message")
    private String message;

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
        return "ComplainModle{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
