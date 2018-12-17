package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */

import com.squareup.moshi.Json;

public class TehsilsList {

    @Json(name = "tehsilId")
    private String tehsilId;
    @Json(name = "tehsilTitle")
    private String tehsilTitle;

    public TehsilsList(String tehsilId, String tehsilTitle) {
        this.tehsilId = tehsilId;
        this.tehsilTitle = tehsilTitle;
    }

    public String getTehsilId() {
        return tehsilId;
    }

    public void setTehsilId(String tehsilId) {
        this.tehsilId = tehsilId;
    }

    public String getTehsilTitle() {
        return tehsilTitle;
    }

    public void setTehsilTitle(String tehsilTitle) {
        this.tehsilTitle = tehsilTitle;
    }

    @Override
    public String toString() {
        return "TehsilsList{" +
                "tehsilId='" + tehsilId + '\'' +
                ", tehsilTitle='" + tehsilTitle + '\'' +
                '}';
    }
}
