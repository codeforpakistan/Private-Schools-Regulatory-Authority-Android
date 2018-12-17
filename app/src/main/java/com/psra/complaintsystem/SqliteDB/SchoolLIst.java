package com.psra.complaintsystem.SqliteDB;

/**
 * Created by HP on 8/16/2018.
 */

import java.util.List;
import com.squareup.moshi.Json;

public class SchoolLIst {

    @Json(name = "tehsils_list")
    private List<TehsilsList> tehsilsList = null;
    @Json(name = "uc_list")
    private List<UcList> ucList = null;
    @Json(name = "schoolslist")
    private List<Schoolslist> schoolslist = null;
    @Json(name = "success")
    private Integer success;

    public List<TehsilsList> getTehsilsList() {
        return tehsilsList;
    }

    public void setTehsilsList(List<TehsilsList> tehsilsList) {
        this.tehsilsList = tehsilsList;
    }

    public List<UcList> getUcList() {
        return ucList;
    }

    public void setUcList(List<UcList> ucList) {
        this.ucList = ucList;
    }

    public List<Schoolslist> getSchoolslist() {
        return schoolslist;
    }

    public void setSchoolslist(List<Schoolslist> schoolslist) {
        this.schoolslist = schoolslist;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }


    @Override
    public String toString() {
        return "SchoolLIst{" +
                "tehsilsList=" + tehsilsList +
                ", ucList=" + ucList +
                ", schoolslist=" + schoolslist +
                ", success=" + success +
                '}';
    }
}
