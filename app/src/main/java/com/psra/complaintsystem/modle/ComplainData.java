package com.psra.complaintsystem.modle;

/**
 * Created by HP on 11/7/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;
@Entity
public class ComplainData {

    @PrimaryKey(autoGenerate = false)
    @Json(name = "notificationId")
    private Integer notificationId;
    @Json(name = "user_id")
    private Integer user_id;
    @Json(name = "complain_id")
    private String complainId;
    @Json(name = "complainTypeTitle")
    private String complainTypeTitle;
    @Json(name = "complainDetail")
    private String complainDetail;
    @Json(name = "statusTitle")
    private String statusTitle;
    @Json(name = "date")
    private String date;

    public ComplainData(String complainId, String statusTitle, String complainTypeTitle, String date, String complainDetail,Integer notificationId, Integer user_id) {
        this.complainId = complainId;
        this.statusTitle = statusTitle;
        this.complainTypeTitle = complainTypeTitle;
        this.date = date;
        this.complainDetail = complainDetail;
        this.notificationId = notificationId;
        this.user_id = user_id;

    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public String getComplainTypeTitle() {
        return complainTypeTitle;
    }

    public void setComplainTypeTitle(String complainTypeTitle) {
        this.complainTypeTitle = complainTypeTitle;
    }

    public String getComplainDetail() {
        return complainDetail;
    }

    public void setComplainDetail(String complainDetail) {
        this.complainDetail = complainDetail;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public String toString() {
        return "ComplainData{" +
                "notificationId=" + notificationId +
                ", user_id=" + user_id +
                ", complainId='" + complainId + '\'' +
                ", complainTypeTitle='" + complainTypeTitle + '\'' +
                ", complainDetail='" + complainDetail + '\'' +
                ", statusTitle='" + statusTitle + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}





