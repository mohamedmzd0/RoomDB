package com.example.roomdb.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles", foreignKeys = {@ForeignKey(entity = Sections.class, parentColumns = "section_id", childColumns = "section_id")})
public class Articles {
    @PrimaryKey
    @ColumnInfo(name = "article_id")
    public int id;
    @ColumnInfo(name = "article_title")
    public String artilce_atitle;
    @ColumnInfo(name = "section_id")
    public int section_id;

    @NonNull
    @Override
    public String toString() {
        return "id : " + id + " , title : " + artilce_atitle + " , section id : " + section_id;
    }
}
