package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;
@Entity
public class Districtlist {
    @NonNull
    @PrimaryKey
    @Json(name = "districtId")
    private String districtId;
    @Json(name = "districtTitle")
    private String districtTitle;

    public Districtlist() {
    }

    public Districtlist(@NonNull String districtId, String districtTitle) {
        this.districtId = districtId;
        this.districtTitle = districtTitle;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }


    @Override
    public String toString() {
        return "Districtlist{" +
                "districtId='" + districtId + '\'' +
                ", districtTitle='" + districtTitle + '\'' +
                '}';
    }
}