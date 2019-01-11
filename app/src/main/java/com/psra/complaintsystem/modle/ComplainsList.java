package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/23/2018.
 */


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.psra.complaintsystem.dbclasses.DbConstants;
import com.squareup.moshi.Json;

@Entity
public class ComplainsList {
    @NonNull
    @PrimaryKey
    @Json(name = "complain_id")
    private String complainId;

    @Json(name = "complain_type_id")
    private String complainTypeId;
    @Json(name = "complainTypeTitle")
    private String complainTypeTitle;
    @Json(name = "complainDetail")
    private String complainDetail;
    @Json(name = "school_id")
    private String schoolId;
    @Json(name = "schoolName")
    private String schoolName;
    @Json(name = "schoolOthers")
    private String schoolOtherName;
    @Json(name = "status_id")
    private String statusId;
    @Json(name = "statusTitle")
    private String statusTitle;
    @Json(name = "dated")
    private String dated;
    @Json(name = "updatedDate")
    private String updatedDate;
    @Json(name = "district_id")
    private String districtId;
    @Json(name = "districtTitle")
    private String districtTitle;
    @Json(name = "user_id")
    private String userId;
    @Json(name = "show")
    private Integer show;

    public String getSchoolOtherName() {
        return schoolOtherName;
    }

    public void setSchoolOtherName(String schoolOtherName) {
        this.schoolOtherName = schoolOtherName;
    }

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
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

    public String getComplainDetail() {
        return complainDetail;
    }

    public void setComplainDetail(String complainDetail) {
        this.complainDetail = complainDetail;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }


    @Override
    public String toString() {
        return "ComplainsList{" +
                "complainId='" + complainId + '\'' +
                ", complainTypeId='" + complainTypeId + '\'' +
                ", complainTypeTitle='" + complainTypeTitle + '\'' +
                ", complainDetail='" + complainDetail + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolOtherName='" + schoolOtherName + '\'' +
                ", statusId='" + statusId + '\'' +
                ", statusTitle='" + statusTitle + '\'' +
                ", dated='" + dated + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", districtId='" + districtId + '\'' +
                ", districtTitle='" + districtTitle + '\'' +
                ", userId='" + userId + '\'' +
                ", show=" + show +
                '}';
    }
}

