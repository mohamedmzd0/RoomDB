package com.example.roomdb.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sections")
public class Sections {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "section_id")
    public int id;
    @ColumnInfo(name = "section_title")
    public String title;

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "id : " + id + " , title : " + title;
    }
}
