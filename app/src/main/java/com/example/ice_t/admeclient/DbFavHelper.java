package com.example.ice_t.admeclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ice-t on 30.08.2017.
 */

public class DbFavHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "creating_database";

    public DbFavHelper(Context context) {
        // конструктор суперкласса
        super(context, "favDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table favorites ("
                + "id integer primary key autoincrement,"
                + "title text,"
                + "imageUrl text"
                + "description text"
                + "detailsUrl text" +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
