# 📚 Room vs SQLite in Android Development

Room and SQLite are both related to databases in Android development, but they serve different purposes. One is the underlying engine, the other is a higher-level abstraction built on top of it.

---

## 📌 SQLite Database

**What is it?**  
- SQLite is a lightweight, serverless, self-contained relational database engine.  
- It’s embedded inside Android apps — no separate installation or server required.  

**Why was it introduced?**  
- To provide an efficient, cross-platform database solution for scenarios where a full database server is unnecessary.  
- Perfect for mobile apps and embedded systems.  

---

## 📌 Room Database

**What is it?**  
- Room is an abstraction layer built on top of SQLite.  
- It provides an object-oriented, developer-friendly API for Android apps.  

**Why was it introduced?**  
- To simplify the complexity of working with raw SQLite.  
- Offers features like compile-time query checking, LiveData/Flow support, and integration with Android Architecture Components.  

---

## 🔄 Room vs SQLite — Feature Comparison

| Feature                | Room DB ✅                               | SQLite ⚙️                      |
|-------------------------|------------------------------------------|--------------------------------|
| **Setup**              | Add dependencies, define entities/DAOs   | Extend `SQLiteOpenHelper`      |
| **Schema definition**  | Annotated entity classes                 | SQL `CREATE TABLE` statements  |
| **Insert data**        | `@Insert` in DAO                        | `db.insert()`                  |
| **Query data**         | `@Query` in DAO                         | `db.query()` / raw SQL         |
| **Update data**        | `@Update` in DAO                        | `db.update()`                  |
| **Delete data**        | `@Delete` in DAO                        | `db.delete()`                  |
| **Error handling**     | Built-in transactions, type safety       | Manual exception handling      |
| **Performance**        | Uses in-memory cache, optimized writes   | Good for large datasets, more I/O |
| **Boilerplate**        | Minimal, annotation-based                | High, lots of SQL strings      |
| **Migrations**         | Automatic / versioned                    | Manual                         |

---

## 🚀 Example: Using Room in Java

### 1. Add Dependencies
```gradle
implementation "androidx.room:room-runtime:2.x.x"
annotationProcessor "androidx.room:room-compiler:2.x.x" 
```
### 2. Create Entity
```java
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int userId;

    public String userName;
}
```
### 3. Define DAO
```java
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE userId = :id")
    User getUserById(int id);
}
```
### 4. Create Database
```java
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```
### 5. Use the Database
```java
import androidx.room.Room;

// inside an Activity or Application class
AppDatabase db = Room.databaseBuilder(
        getApplicationContext(),
        AppDatabase.class,
        "my-database"
).build();

UserDao userDao = db.userDao();
User user = new User();
user.userId = 1;
user.userName = "John Doe";

new Thread(() -> {
    userDao.insertUser(user);
    User retrieved = userDao.getUserById(1);
}).start();
```
## 🚀 Example: Using SQLite in Java
### 1. Create Contract
```java
import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_ID = "userId";
        public static final String COLUMN_NAME = "userName";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME + " TEXT)";
    }
}
```
### 2. Create Helper
```java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "my-database.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.UserTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle migrations
    }
}
```
### 3. Insert Data
```java
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

// inside Activity
DatabaseHelper dbHelper = new DatabaseHelper(this);
SQLiteDatabase db = dbHelper.getWritableDatabase();

ContentValues values = new ContentValues();
values.put(DatabaseContract.UserTable.COLUMN_ID, 1);
values.put(DatabaseContract.UserTable.COLUMN_NAME, "John Doe");

long newRowId = db.insert(DatabaseContract.UserTable.TABLE_NAME, null, values);
```
### 4. Query Data
```java
import android.database.Cursor;

// inside Activity
String[] projection = {
        DatabaseContract.UserTable.COLUMN_ID,
        DatabaseContract.UserTable.COLUMN_NAME
};

String selection = DatabaseContract.UserTable.COLUMN_ID + " = ?";
String[] selectionArgs = { "1" };

Cursor cursor = db.query(
        DatabaseContract.UserTable.TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        null
);

if (cursor.moveToFirst()) {
    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserTable.COLUMN_NAME));
}
cursor.close();
```
## 📝 Summary

>SQLite = powerful, low-level database engine (manual SQL, more boilerplate).

>Room = abstraction layer over SQLite (simpler, safer, integrates with Android components).

>Use Room for modern Android development (recommended).

>Use SQLite directly if you need fine-grained control or special SQL features.
