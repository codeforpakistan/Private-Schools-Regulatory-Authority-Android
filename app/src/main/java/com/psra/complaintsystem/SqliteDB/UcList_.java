package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */

import com.squareup.moshi.Json;

public class UcList_ {

    @Json(name = "ucId")
    private String ucId;
    @Json(name = "ucTitle")
    private String ucTitle;

    public UcList_(String ucId, String ucTitle) {
        this.ucId = ucId;
        this.ucTitle = ucTitle;
    }

    public String getUcId() {
        return ucId;
    }

    public void setUcId(String ucId) {
        this.ucId = ucId;
    }

    public String getUcTitle() {
        return ucTitle;
    }

    public void setUcTitle(String ucTitle) {
        this.ucTitle = ucTitle;
    }

    @Override
    public String toString() {
        return "UcList_{" +
                "ucId='" + ucId + '\'' +
                ", ucTitle='" + ucTitle + '\'' +
                '}';
    }
}
