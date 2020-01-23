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
                "name of database").allowMainThreadQueries().build();
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
