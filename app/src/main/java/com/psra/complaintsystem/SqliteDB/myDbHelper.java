package com.psra.complaintsystem.SqliteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 8/16/2018.
 */

public class myDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase";
    private static final int DATABASE_Version = 1;

    private static final String D_TABLE_NAME = "District";
    private static final String T_TABLE_NAME = "Tehsils";
    private static final String U_TABLE_NAME = "Ucs";
    private static final String S_TABLE_NAME = "School";


    private static final String D_ID="D_id";
    private static final String T_ID="T_id";
    private static final String U_ID="U_id";
    private static final String S_ID="S_id";


    private static final String D_NAME = "D_Name";
    private static final String T_NAME = "T_Name";
    private static final String U_NAME = "U_Name";
    private static final String S_NAME = "S_Name";

    private static final String D_CREATE_TABLE = "CREATE TABLE "+D_TABLE_NAME+
            " ("+D_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+D_NAME+" VARCHAR(255));";

    private static final String T_CREATE_TABLE = "CREATE TABLE "+T_TABLE_NAME+
            " ("+T_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+T_NAME+" VARCHAR(255));";

    private static final String U_CREATE_TABLE = "CREATE TABLE "+U_TABLE_NAME+
            " ("+U_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+U_NAME+" VARCHAR(255));";

    private static final String S_CREATE_TABLE = "CREATE TABLE "+S_TABLE_NAME+
            " ("+S_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+S_NAME+" VARCHAR(255));";

    private static final String D_DROP_TABLE ="DROP TABLE IF EXISTS "+D_TABLE_NAME;
    private static final String T_DROP_TABLE ="DROP TABLE IF EXISTS "+T_TABLE_NAME;
    private static final String U_DROP_TABLE ="DROP TABLE IF EXISTS "+U_TABLE_NAME;
    private static final String S_DROP_TABLE ="DROP TABLE IF EXISTS "+S_TABLE_NAME;

    private Context context;
    public myDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        try {
            db.execSQL(D_CREATE_TABLE);
            db.execSQL(T_CREATE_TABLE);
            db.execSQL(U_CREATE_TABLE);
            db.execSQL(S_CREATE_TABLE);

        } catch (Exception e) {
            Message.message(context,""+e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            Message.message(context,"OnUpgrade");
            db.execSQL(D_DROP_TABLE);
            db.execSQL(T_DROP_TABLE);
            db.execSQL(U_DROP_TABLE);
            db.execSQL(S_DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Message.message(context,""+e);
        }

    }
}
