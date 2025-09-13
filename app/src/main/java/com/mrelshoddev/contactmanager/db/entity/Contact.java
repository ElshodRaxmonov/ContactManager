package com.mrelshoddev.contactmanager.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    // declared on Entity annotation
    // public static final String TABLE_NAME = "contacts";
    // they declared under ColumnInfo annotation
    // public static final String COLUMN_ID = "contact_id";
    // public static final String COLUMN_NAME = "contact_name";
    // public static final String COLUMN_NUMBER = "contact_number";

    @ColumnInfo(name = "contact_name")
    private String name;
    @ColumnInfo(name = "contact_number")
    private String number;
    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Ignore
    public Contact() {
//  When Room generates codes for us, room uses only one constructor.
//  If we have more than one constructors, we should tell
//  Room to ignore them using @Ignore .
    }

    public Contact(long id, String name, String number) {
        this.name = name;
        this.number = number;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public long getId() {
        return id;
    }

//      This large query does not need when we are using room library
//    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
//            + TABLE_NAME + "(" +
//            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            COLUMN_NAME + " TEXT," +
//            COLUMN_NUMBER + " TEXT)";
}
