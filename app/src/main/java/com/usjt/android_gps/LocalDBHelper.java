package com.usjt.android_gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "local.db";
    private static final int DB_VERSION = 1;
    public LocalDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocalContract.createTableLocal());
        db.execSQL(LocalContract.insertChamados());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LocalContract.insertChamados());
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL(LocalContract.Local.DROP_TABLE);
        onCreate(db);
    }
}
