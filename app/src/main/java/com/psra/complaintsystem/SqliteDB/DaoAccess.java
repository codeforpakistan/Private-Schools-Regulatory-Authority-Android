package com.psra.complaintsystem.SqliteDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.psra.complaintsystem.dbclasses.DbConstants;
import com.psra.complaintsystem.modle.ComplainData;
import com.psra.complaintsystem.modle.ComplainsList;
import com.psra.complaintsystem.modle.NotifModule;

import java.util.List;

/**
 * Created by HP on 8/18/2018.
 */
@Dao
public interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleDist (List<Districtlist> districtlist);


    @Query("SELECT * FROM Districtlist")
    Districtlist fetchDistrictlist ();

    @Query("DELETE FROM Districtlist ")
    void deleteDistrictlist();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleComplainTypesList (List<ComplainTypesList> complainTypesList);

    @Query("SELECT * FROM ComplainTypesList")
    ComplainTypesList fetchComplainTypesList();


    @Query("DELETE FROM ComplainTypesList")
    void deleteComplaintTypelist();

    ////////////////////////////////////////////////////////////
    @Insert
    void insertComplaintsList(List<ComplainsList> complainsList);

    @Delete
    void deleteComplaintList(List<ComplainsList> complainsList);

    @Query("SELECT * FROM ComplainsList")
    List<ComplainsList> getAllComplaints();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(List<ComplainData> complainsList);

    @Query("SELECT * FROM ComplainData")
    List<ComplainData> getAllNoficitions();

    @Query("DELETE FROM ComplainData WHERE notificationId = :userId")
    void deleteNotification(int userId);


}
