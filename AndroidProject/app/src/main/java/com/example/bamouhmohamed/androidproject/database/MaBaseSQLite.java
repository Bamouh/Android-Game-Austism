package com.example.bamouhmohamed.androidproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BamouhMohamed on 26/03/2018.
 */

public class MaBaseSQLite extends SQLiteOpenHelper {


    private static final String TABLE_PERSONNE = "table_personne";
    private static final String COL_ID = "ID";
    private static final String COL_LABEL = "label";
    private static final String COL_IMG = "img";
    private static final String COL_ICON = "icon";
    private static final String COL_AUDIO = "audio";
    private static final String COL_IMAGE = "image";
    private static final String CREATE_TABLE_PERSONNE = "CREATE TABLE "
            + TABLE_PERSONNE
            + " ("
            + COL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_LABEL
            + " TEXT NOT NULL, "
            + COL_IMG
            + " INTEGER , "
            + COL_ICON
            + " BLOB, "
            + COL_AUDIO
            + " BLOB, "
            + COL_IMAGE
            + " TEXT );";
    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_PERSONNE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE "+TABLE_PERSONNE+" ;");
        onCreate(db);
    }
}
