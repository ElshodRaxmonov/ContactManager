package com.mrelshoddev.contactmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mrelshoddev.contactmanager.adapter.ContactsAdapter;
import com.mrelshoddev.contactmanager.adapter.ItemDecoration;
import com.mrelshoddev.contactmanager.db.entity.Contact;
import com.mrelshoddev.contactmanager.db.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> arrayList = new ArrayList<>();
    private RecyclerView listView;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts Manager");


        listView = findViewById(R.id.recycler_view_contacts);
        db = new DatabaseHelper(this);

        arrayList.addAll(db.getAllContacts());
        contactsAdapter = new ContactsAdapter(this, arrayList, MainActivity.this);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new ItemDecoration(this, 12));
        listView.setAdapter(contactsAdapter);

        FloatingActionButton fab = findViewById(R.id.add_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContacts(false, null, -1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAndEditContacts(final boolean isUpdate, final Contact contact, final int position) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContactName = view.findViewById(R.id.name);
        final EditText newContactSurname = view.findViewById(R.id.surname);
        final EditText contactNumber = view.findViewById(R.id.number);

        contactTitle.setText(!isUpdate ? "Add New Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            newContactSurname.setVisibility(View.GONE);
            newContactName.setText(contact.getName());
            contactNumber.setText(contact.getNumber());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(isUpdate ? "Delete" : "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {

                                    deleteContact(contact, position);
                                } else {

                                    dialogBox.cancel();

                                }

                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(newContactName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter contact name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && contact != null) {

                    updateContact(newContactName.getText().toString(), contactNumber.getText().toString(), position);
                } else {
                    String fullName = newContactName.getText().toString() + newContactSurname.getText().toString();
                    createContact(fullName, contactNumber.getText().toString());
                }
            }
        });
    }

    private void deleteContact(Contact contact, int position) {

        arrayList.remove(position);
        db.deleteContact(contact);
        contactsAdapter.notifyDataSetChanged();
    }

    private void updateContact(String name, String number, int position) {

        Contact contact = arrayList.get(position);

        contact.setName(name);
        contact.setNumber(number);

        db.updateContact(contact);

        arrayList.set(position, contact);

        contactsAdapter.notifyDataSetChanged();


    }

    private void createContact(String name, String email) {

        long id = db.insertContact(name, email);


        Contact contact = db.getContact(id);

        if (contact != null) {

            arrayList.add(0, contact);
            contactsAdapter.notifyDataSetChanged();

        }

    }


}