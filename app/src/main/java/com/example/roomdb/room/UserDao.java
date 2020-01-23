package com.example.roomdb.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roomdb.model.Articles;
import com.example.roomdb.model.Sections;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM articles where section_id = :id")
    public Articles getById(int id);


    @Query("SELECT * FROM articles ")
    public List<Articles> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertArticles(Articles articles);

    @Delete
    public void deleteArtilce(Articles articles);

    //////////////////

    @Query("SELECT * FROM  sections ")
    public List<Sections> getAllSections();

    @Query("SELECT * FROM sections WHERE section_id =:id")
    public Sections getSectionByID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSection(Sections sections);

    @Delete
    public void deleteSection(Articles articles);

    @Query("SELECT sections.section_title FROM sections INNER JOIN articles  ON articles.article_id=sections.section_id WHERE  articles.article_id=:id  ")
    public String getSectionName(int id);

}
