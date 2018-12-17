package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/13/2018.
 */

import com.squareup.moshi.Json;

public class UserInfoList {

    @Json(name = "userId")
    private String userId;
    @Json(name = "userTitle")
    private String userTitle;
    @Json(name = "userPassword")
    private String userPassword;
    @Json(name = "userEmail")
    private String userEmail;
    @Json(name = "cnic")
    private String cnic;
    @Json(name = "contactNumber")
    private String contactNumber;
    @Json(name = "districtTitle")
    private String districtTitle;
    @Json(name = "address")
    private String address;
    @Json(name = "gender")
    private String gender;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "UserInfoList{" +
                "userId='" + userId + '\'' +
                ", userTitle='" + userTitle + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", cnic='" + cnic + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", districtTitle='" + districtTitle + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}