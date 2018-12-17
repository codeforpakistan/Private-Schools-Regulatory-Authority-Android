package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/12/2018.
 */

import com.squareup.moshi.Json;

public class Logindatum {

    @Json(name = "api_token")
    private String apiToken;
    @Json(name = "usertypeid")
    private String usertypeid;
    @Json(name = "mobile")
    private String mobile;
    @Json(name = "userid")
    private String userid;
    @Json(name = "username")
    private String username;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(String usertypeid) {
        this.usertypeid = usertypeid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    @Override
    public String toString() {
        return "Logindatum{" +
                "apiToken='" + apiToken + '\'' +
                ", usertypeid='" + usertypeid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
