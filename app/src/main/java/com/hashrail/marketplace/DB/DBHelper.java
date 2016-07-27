package com.hashrail.marketplace.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dreamworld Solutions on 16-7-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "seemora";
    public static final String TB_ENCODEDIMAGE = "encoded";
    public static final int DBVERSION = 1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sd) {

        sd.execSQL("create table IF NOT EXISTS " + TB_ENCODEDIMAGE + "(encodedImage Blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sd, int oldVersion, int newVersion) {

    }
}
