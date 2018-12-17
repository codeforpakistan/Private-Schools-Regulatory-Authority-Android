package com.psra.complaintsystem.modle;

/**
 * Created by HP on 7/23/2018.
 */

import java.util.List;
import com.squareup.moshi.Json;

public class GetComplaintDatum {

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
        return "GetComplaintDatum{" +
                "success=" + success +
                ", complainsList=" + complainsList +
                '}';
    }
}
