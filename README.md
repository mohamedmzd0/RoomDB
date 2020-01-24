# RoomDB
Simple ROOM Example
first you need to add Room dependency to build.gradle file

```
    implementation "android.arch.persistence.room:runtime:1.1.1"
    annotationProcessor "android.arch.persistence.room:compiler:1.1.1"
    
```

-----------------------------

## create DatabaseClient class

```
public class DatabaseClient {
    private static DatabaseClient mInstance;
    //our app database object
    private final ArticlesDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        //creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(mCtx, ArticlesDatabase.class,
        /// name of databse , allow to run on UI thread 
                "name of database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
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
```

---------------
## define your tables in Database class and version
```
// tables in our database for exmaple i have not tables 
@Database(entities = {Articles.class, Sections.class}, version = 1)
public abstract class ArticlesDatabase extends RoomDatabase {
    public abstract UserDao Dao();
}

```
--------------

## define our interface which have the prototybe of all methods 

```
// don't forget to add @Dao annotation at first
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
/// join to tables , you must add where clause add last.
    @Query("SELECT sections.section_title FROM sections INNER JOIN articles  ON articles.article_id=sections.section_id WHERE  articles.article_id=:id  ")
    public String getSectionName(int id);

}


```
--------------
## our models classes 

### all filds must be public otherwise you will get an error

```
section_id column references a foreign key but it is not part of an index.
This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.

```
![Annotation 2020-01-23 120749](https://user-images.githubusercontent.com/22664709/72975162-00de7d00-3dd9-11ea-9514-53971d041c20.png)

* first 
```
// table name
@Entity(tableName = "sections")
public class Sections {

    @PrimaryKey(autoGenerate = true)
//the name of the column in table 
    @ColumnInfo(name = "section_id")
// all filds must be public  
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

```
* second 

```

// table name , and list of the Foreign keys (parent class , parent class primary key , the foreign key in this class )
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

```

-------------------
## test your code in mainactivity class
```
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
```

#### some resources you may need 
## [codepath](https://guides.codepath.com/android/Room-Guide)
## [androidkt](https://androidkt.com/database-relationships/)
