package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */

import com.squareup.moshi.Json;

import java.util.List;

public class DistList {

    @Json(name = "complainTypesList")
    private List<ComplainTypesList> complainTypesList = null;
    @Json(name = "districtlist")
    private List<Districtlist> districtlist = null;

    public List<ComplainTypesList> getComplainTypesList() {
        return complainTypesList;
    }

    public void setComplainTypesList(List<ComplainTypesList> complainTypesList) {
        this.complainTypesList = complainTypesList;
    }

    public List<Districtlist> getDistrictlist() {
        return districtlist;
    }

    public void setDistrictlist(List<Districtlist> districtlist) {
        this.districtlist = districtlist;
    }

    @Override
    public String toString() {
        return "DistList{" +
                "complainTypesList=" + complainTypesList +
                ", districtlist=" + districtlist +
                '}';
    }
}