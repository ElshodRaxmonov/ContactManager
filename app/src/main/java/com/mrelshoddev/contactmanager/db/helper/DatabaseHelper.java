package com.mrelshoddev.contactmanager.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mrelshoddev.contactmanager.db.entity.Contact;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contact_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // to create database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Contact.CREATE_TABLE);
    }

    // to update database
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersin, int newVersion) {
        if(oldVersin==1 && newVersion==2) {
            db.execSQL("ALTER TABLE contacts RENAME TO contacts_old");
            db.execSQL(Contact.CREATE_TABLE);
            db.execSQL("INSERT INTO contacts (contact_id,contact_name)"+"SELECT contact_id,contact_name FROM contacts_old");
            db.execSQL("DROP TABLE contacts_old");
        }
    }

    public long insertContact(String name, String number) {
        SQLiteDatabase db = this.getWritableDatabase(); // writable
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME, name);
        values.put(Contact.COLUMN_NUMBER, number);


        long id = db.insert(Contact.TABLE_NAME, null, values);
        db.close();
        return id;
    }


    public Contact getContact(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // readable
        Cursor cr = db.query(Contact.TABLE_NAME, new String[]{
                        Contact.COLUMN_ID, Contact.COLUMN_NAME, Contact.COLUMN_NUMBER
                }, Contact.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cr != null) {
            cr.moveToFirst();
        }
        Contact contact = new Contact(
                cr.getLong(cr.getColumnIndexOrThrow(Contact.COLUMN_ID)),
                cr.getString(cr.getColumnIndexOrThrow(Contact.COLUMN_NAME)),
                cr.getString(cr.getColumnIndexOrThrow(Contact.COLUMN_NUMBER))
        );
        cr.close();
        return contact;
    }


    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Contact.TABLE_NAME + " ORDER BY " + Contact.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Contact.COLUMN_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NAME)));
                contact.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NUMBER)));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();

        return contacts;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_NUMBER, contact.getNumber());
        return db.update(Contact.TABLE_NAME, values, Contact.COLUMN_ID + " =?",
                new String[]{String.valueOf(contact.getId())});

    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Contact.TABLE_NAME, Contact.COLUMN_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

}
