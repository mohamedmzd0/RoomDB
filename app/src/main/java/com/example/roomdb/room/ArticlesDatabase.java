package com.example.roomdb.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdb.model.Articles;
import com.example.roomdb.model.Sections;

@Database(entities = {Articles.class, Sections.class}, version = 1)
public abstract class ArticlesDatabase extends RoomDatabase {
    public abstract UserDao Dao();
}
