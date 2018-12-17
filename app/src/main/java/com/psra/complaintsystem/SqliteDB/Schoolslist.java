package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */

import com.squareup.moshi.Json;

public class Schoolslist {

    @Json(name = "schoolId")
    private String schoolId;
    @Json(name = "schoolName")
    private String schoolName;

    public Schoolslist() {
    }

    public Schoolslist(String schoolId, String schoolName) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "Schoolslist{" +
                "schoolId='" + schoolId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
