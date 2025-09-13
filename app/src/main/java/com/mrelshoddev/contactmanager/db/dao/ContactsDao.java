package com.mrelshoddev.contactmanager.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mrelshoddev.contactmanager.db.entity.Contact;

import java.util.List;

@Dao
public interface ContactsDao {
    @Insert
    public long insertContact(Contact contact);
    //    public long insertContact(String name, String number) {
    //        SQLiteDatabase db = this.getWritableDatabase(); // writable
    //        ContentValues values = new ContentValues();
    //        values.put(Contact.COLUMN_NAME, name);
    //        values.put(Contact.COLUMN_NUMBER, number);
    //
    //
    //        long id = db.insert(Contact.TABLE_NAME, null, values);
    //        db.close();
    //        return id;
    //    }
    @Query("select * from contacts")
    public List<Contact> getAllContacts();
    //    public ArrayList<Contact> getAllContacts() {
    //        ArrayList<Contact> contacts = new ArrayList<>();
    //        String selectQuery = "SELECT * FROM " + Contact.TABLE_NAME + " ORDER BY " + Contact.COLUMN_ID + " DESC";
    //
    //        SQLiteDatabase db = this.getWritableDatabase();
    //        Cursor cursor = db.rawQuery(selectQuery, null);
    //
    //        if (cursor.moveToFirst()) {
    //            do {
    //                Contact contact = new Contact();
    //                contact.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Contact.COLUMN_ID)));
    //                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NAME)));
    //                contact.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NUMBER)));
    //                contacts.add(contact);
    //            } while (cursor.moveToNext());
    //        }
    //        db.close();
    //
    //        return contacts;
    //    }
    @Query("select * from contacts where contact_id = :contact_id")
    public Contact getContact(long contact_id);
    //    public Contact getContact(long id) {
    //        SQLiteDatabase db = this.getReadableDatabase(); // readable
    //        Cursor cr = db.query(Contact.TABLE_NAME, new String[]{
    //                        Contact.COLUMN_ID, Contact.COLUMN_NAME, Contact.COLUMN_NUMBER
    //                }, Contact.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
    //                null,
    //                null,
    //                null,
    //                null);
    //        if (cr != null) {
    //            cr.moveToFirst();
    //        }
    //        Contact contact = new Contact(
    //                cr.getLong(cr.getColumnIndexOrThrow(Contact.COLUMN_ID)),
    //                cr.getString(cr.getColumnIndexOrThrow(Contact.COLUMN_NAME)),
    //                cr.getString(cr.getColumnIndexOrThrow(Contact.COLUMN_NUMBER))
    //        );
    //        cr.close();
    //        return contact;
    //    }
    @Update
    public void updateContact(Contact contact);
    //    public int updateContact(Contact contact) {
    //        SQLiteDatabase db = this.getWritableDatabase();
    //        ContentValues values = new ContentValues();
    //        values.put(Contact.COLUMN_NAME, contact.getName());
    //        values.put(Contact.COLUMN_NUMBER, contact.getNumber());
    //        return db.update(Contact.TABLE_NAME, values, Contact.COLUMN_ID + " =?",
    //                new String[]{String.valueOf(contact.getId())});
    //
    //    }
    @Delete
    public void deleteContact(Contact contact);
    //    public void deleteContact(Contact contact) {
    //        SQLiteDatabase db = this.getWritableDatabase();
    //        db.delete(Contact.TABLE_NAME, Contact.COLUMN_ID + "=?",
    //                new String[]{String.valueOf(contact.getId())});
    //        db.close();
    //    }
}
