package com.psra.complaintsystem.SqliteDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.psra.complaintsystem.modle.ComplainData;
import com.psra.complaintsystem.modle.ComplainsList;

/**
 * Created by HP on 8/18/2018.
 */

@Database (entities = {Districtlist.class,ComplainTypesList.class, ComplainsList.class,ComplainData.class}, version = 2, exportSchema = false)
public abstract class DatabaseRooom extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;
}
