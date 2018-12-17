package com.psra.complaintsystem.SqliteDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by HP on 10/15/2018.
 */
@Entity
public class NotificationModule {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    String st_id;
    String status;
    String complaintTitle;
    String date;
    String complaintDetail;

    public NotificationModule(@NonNull String st_id, String status, String complaintTitle, String date, String complaintDetail) {
        this.st_id = st_id;
        this.status = status;
        this.complaintTitle = complaintTitle;
        this.date = date;
        this.complaintDetail = complaintDetail;
    }

    public String getComplaintDetail() {
        return complaintDetail;
    }

    public void setComplaintDetail(String complaintDetail) {
        this.complaintDetail = complaintDetail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(@NonNull String st_id) {
        this.st_id = st_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    @Override
    public String toString() {
        return "NotificationModule{" +
                "st_id='" + st_id + '\'' +
                ", status='" + status + '\'' +
                ", complaintTitle='" + complaintTitle + '\'' +
                ", date='" + date + '\'' +
                ", complaintDetail='" + complaintDetail + '\'' +
                '}';
    }
}