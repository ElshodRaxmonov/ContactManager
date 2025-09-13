package com.mrelshoddev.contactmanager.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mrelshoddev.contactmanager.db.dao.ContactsDao;
import com.mrelshoddev.contactmanager.db.entity.Contact;

@Database(entities = {Contact.class},version=1)
public abstract class ContactsDatabase extends RoomDatabase {

    public abstract ContactsDao getContactDao();
}
