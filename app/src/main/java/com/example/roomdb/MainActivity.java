package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.roomdb.model.Articles;
import com.example.roomdb.model.Sections;
import com.example.roomdb.room.ArticlesDatabase;
import com.example.roomdb.room.DatabaseClient;
import com.example.roomdb.room.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "_________";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDao data = DatabaseClient.getInstance(getApplication()).getAppDatabase()
                .Dao();

        Sections sections = new Sections();
        sections.id = 1;
        sections.title = "section 1";
        data.insertSection(sections);
        sections.id = 2;
        sections.title = "section 2";
        data.insertSection(sections);


        List<Sections> sectionsList = data.getAllSections();
        for (int i = 0; i < sectionsList.size(); i++) {
            Log.d(TAG, "onCreate: " + sectionsList.get(i).toString());
        }

        Log.d(TAG, "onCreate: item with id 1 : " + data.getSectionByID(1).toString());
        Log.d(TAG, "onCreate: item with id 3 : " + (data.getSectionByID(3) != null ? data.getSectionByID(3).toString() : "not found"));


        Articles articles = new Articles();
        articles.id = 1;
        articles.artilce_atitle = "article 1";
        articles.section_id = 1;
        data.insertArticles(articles);


        articles.id = 2;
        articles.artilce_atitle = "article 2";
        articles.section_id = 1;
        data.insertArticles(articles);
        articles.id = 3;
        articles.artilce_atitle = "article 3";
        articles.section_id = 2;
        data.insertArticles(articles);


        List<Articles> articlesList = data.getAll();
        for (int i = 0; i < articlesList.size(); i++) {
            Log.d(TAG, "onCreate: " + articlesList.get(i).toString());
        }
        Articles articles1 = new Articles();
        articles1.id = 1;
        data.deleteArtilce(articles1);


        articlesList = data.getAll();
        for (int i = 0; i < articlesList.size(); i++) {
            Log.d(TAG, "onCreate: " + articlesList.get(i).toString());
        }


        Log.d(TAG, "onCreate: " + data.getSectionName(2));
    }
}
