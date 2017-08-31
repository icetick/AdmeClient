package com.example.ice_t.admeclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ice-t on 30.08.2017.
 */

public class DbFavHelper extends SQLiteOpenHelper {

    //constant strings to simplify usage of dbhelper
    private final String KEY_TITLE = "title";
    private final String KEY_IMAGE = "imageUrl";
    private final String KEY_DESCRIPTION = "description";
    private final String KEY_DETAILS = "detailsUrl";
    private final String TABLE_NAME = "favorites";

    public DbFavHelper(Context context) {
        //Basic ctor
        super(context, "favDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LOG_TAG = "creating_database";
        //Debug issues was here
        Log.d(LOG_TAG, "--- onCreate database ---");
        //Creating table favorites
        db.execSQL("create table " + TABLE_NAME + " ("
                + "id integer primary key autoincrement,"
                + KEY_TITLE +" text,"
                + KEY_IMAGE + " text,"
                + KEY_DESCRIPTION + " text,"
                + KEY_DETAILS + " text );");
    }

    //This method is adding item to database
    public void addItem(Article data)
    {
        //Getting database
        SQLiteDatabase db = this.getWritableDatabase();
        //Using collection of key-values
        ContentValues article = new ContentValues();

        //And adding some information from object to query
        article.put(KEY_TITLE, data.getTitle());
        article.put(KEY_DESCRIPTION, data.getDescription());
        article.put(KEY_DETAILS, data.getDetailsUrl());
        article.put(KEY_IMAGE, data.getImageUrl());

        //Insert query
        db.insert(TABLE_NAME, null, article);

        db.close();
    }

    //This method is removing certain object from database
    public void removeItem(Article data)
    {
        //Getting db
        SQLiteDatabase db = this.getWritableDatabase();
        //Removing row from database with same title
        db.delete(TABLE_NAME, KEY_TITLE + " = ?", new String[]{data.getTitle()});
        db.close();
    }

    //This method checks for a similar object in database
    public boolean checkFavorite(Article data)
    {
        //Getting all favorites
        ArrayList<Article> articles = getFavorites();
        //Comparison titles and descriptions in loop
        for(Article item : articles)
        {
            if(item.getTitle().equals(data.getTitle()) && item.getDescription().equals(data.getDescription()))
            {
                return true;
            }
        }
        return false;
    }

    //This method is getting all favorite articles
    public ArrayList<Article> getFavorites()
    {
        //Getting database
        SQLiteDatabase db = this.getWritableDatabase();
        //Creating some colletion to fill in
        ArrayList<Article> articles = new ArrayList<>();
        //Simple Query " Select * from favorites "
        String query = "Select * from " + TABLE_NAME;

        //Creating cursor to go through the rows
        Cursor cursor = db.rawQuery(query, null);
        //Article to fill collection
        Article article;
        //If table have anything
        if (cursor.moveToFirst()){
            //In loop we are going through the rows
            do{
                //Creating some object and adding it to our collection
                article = new Article(cursor.getString(2), cursor.getString(1), cursor.getString(3), cursor.getString(4));
                articles.add(article);
            }
            while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return articles;
    }

    //If we need migration
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
