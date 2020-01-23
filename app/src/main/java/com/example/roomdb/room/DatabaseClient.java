package com.example.roomdb.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private static DatabaseClient mInstance;
    //our app database object
    private final ArticlesDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, ArticlesDatabase.class,
                "MyToDos").allowMainThreadQueries().build();
    }
    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }
    public ArticlesDatabase getAppDatabase() {
        return appDatabase;
    }
}
