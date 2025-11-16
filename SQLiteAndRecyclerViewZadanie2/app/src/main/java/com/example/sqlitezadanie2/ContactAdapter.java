package com.example.sqlitezadanie2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    // Ta metoda tworzy nowy ViewHolder, gdy RecyclerView go potrzebuje.
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    // Ta metoda wypełnia ViewHolder danymi dla określonej pozycji na liście.
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact currentContact = contactList.get(position);
        holder.name.setText(currentContact.getName());
        holder.phone.setText(currentContact.getPhone());
    }

    // Ta metoda zwraca liczbę elementów na liście.
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // Wewnętrzna klasa ViewHolder przechowuje referencje do widoków wiersza.
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name,phone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameText);
            phone = itemView.findViewById(R.id.phoneText);
        }
    }
}
