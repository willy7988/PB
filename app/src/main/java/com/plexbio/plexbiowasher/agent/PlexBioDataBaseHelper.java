package com.plexbio.plexbiowasher.agent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Willy on 2016/11/3.
 */

public class PlexBioDataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "LogFile";
    private static final int DB_VERSION = 1;

    PlexBioDataBaseHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE LOGS(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "FUNCTION TEXT, "
                + "STATUS TEXT, "
                + "_DATETIME DATETIME);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
