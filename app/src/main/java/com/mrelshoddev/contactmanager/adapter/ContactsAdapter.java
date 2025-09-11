package com.mrelshoddev.contactmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.mrelshoddev.contactmanager.MainActivity;
import com.mrelshoddev.contactmanager.R;
import com.mrelshoddev.contactmanager.db.entity.Contact;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contact> contactsList;
    private MainActivity mainActivity;

    public ContactsAdapter(Context context, ArrayList<Contact> arrayList,
                           MainActivity mainActivity) {
        this.context = context;
        this.contactsList = arrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Contact contact = contactsList.get(position);
        holder.name.setText(contact.getName());

        if (contact.getNumber() == null) {

            holder.number.setText("Not created");
        } else {
            holder.number.setText(contact.getNumber());
        }

        holder.itemView.setOnClickListener(view -> mainActivity.addAndEditContacts(true, contact, holder.getAdapterPosition()));
        holder.callButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+contact.getNumber()));
            context.startActivity(intent);
        });
        holder.smsButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms: "+contact.getNumber()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView number;

        private ImageButton callButton;
        private ImageButton smsButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            callButton = itemView.findViewById(R.id.my_call_button);
            smsButton = itemView.findViewById(R.id.my_sms_button);
        }
    }

}
