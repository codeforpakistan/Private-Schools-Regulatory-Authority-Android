package com.psra.complaintsystem.modle;

/**
 * Created by HP on 10/3/2018.
 */

import com.squareup.moshi.Json;

import java.util.List;

public class FeedbackModule {

    @Json(name = "success")
    private Integer success;
    @Json(name = "complains_list")
    private List<ComplainsList> complainsList = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<ComplainsList> getComplainsList() {
        return complainsList;
    }

    public void setComplainsList(List<ComplainsList> complainsList) {
        this.complainsList = complainsList;
    }

    @Override
    public String toString() {
        return "FeedbackModule{" +
                "success=" + success +
                ", complainsList=" + complainsList +
                '}';
    }
}
