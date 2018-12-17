package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;
@Entity
public class ComplainTypesList {
    @NonNull
    @PrimaryKey
    @Json(name = "complainTypeId")
    private String complainTypeId;
    @Json(name = "complainTypeTitle")
    private String complainTypeTitle;

    public ComplainTypesList(@NonNull String complainTypeId, String complainTypeTitle) {
        this.complainTypeId = complainTypeId;
        this.complainTypeTitle = complainTypeTitle;
    }

    public String getComplainTypeId() {
        return complainTypeId;
    }

    public void setComplainTypeId(String complainTypeId) {
        this.complainTypeId = complainTypeId;
    }

    public String getComplainTypeTitle() {
        return complainTypeTitle;
    }

    public void setComplainTypeTitle(String complainTypeTitle) {
        this.complainTypeTitle = complainTypeTitle;
    }


    @Override
    public String toString() {
        return "ComplainTypesList{" +
                "complainTypeId='" + complainTypeId + '\'' +
                ", complainTypeTitle='" + complainTypeTitle + '\'' +
                '}';
    }
}