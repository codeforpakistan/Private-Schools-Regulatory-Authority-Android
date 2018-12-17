package com.psra.complaintsystem.modle;

/**
 * Created by HP on 11/7/2018.
 */

import com.squareup.moshi.Json;

public class Data {

    @Json(name = "complain_data")
    private ComplainData complainData;
    @Json(name = "icon")
    private String icon;
    @Json(name = "sound")
    private String sound;
    @Json(name = "vibrate")
    private Integer vibrate;
    @Json(name = "click_action")
    private String clickAction;

    public ComplainData getComplainData() {
        return complainData;
    }

    public void setComplainData(ComplainData complainData) {
        this.complainData = complainData;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Integer getVibrate() {
        return vibrate;
    }

    public void setVibrate(Integer vibrate) {
        this.vibrate = vibrate;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    @Override
    public String toString() {
        return "Data{" +
                "complainData=" + complainData +
                ", icon='" + icon + '\'' +
                ", sound='" + sound + '\'' +
                ", vibrate=" + vibrate +
                ", clickAction='" + clickAction + '\'' +
                '}';
    }
}