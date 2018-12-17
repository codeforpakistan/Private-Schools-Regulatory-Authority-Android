package com.psra.complaintsystem.dbclasses;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.psra.complaintsystem.modle.ComplainsList;

import java.util.List;


/**
 * Created by HP on 10/15/2018.
 */
@Dao
public interface DbHelper {



    @Delete
    void delete(ComplainsList complainsList);

    @Query("SELECT * FROM "+DbConstants.TABLE_COMPLAINT_LIST)
    List<ComplainsList> getAll();



}
