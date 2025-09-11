package com.mrelshoddev.contactmanager.db.entity;

public class Contact {
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_NUMBER = "contact_number";

    private String name;
    private String number;
    private long id;

    public Contact() {

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
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_NUMBER + " TEXT)";
}
