package com.example.sqlitezadanie2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText nameInput,phoneInput;
    private Button saveButton;
    private RecyclerView contactsRecyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        saveButton = findViewById(R.id.saveButton);
        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);

        contactList = new ArrayList<>();
        adapter = new ContactAdapter(contactList);


        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        // Wyświetl notatki przy starcie
        loadNotes();
    }

    private void addNote() {
        String nameText = nameInput.getText().toString();
        String phoneText = phoneInput.getText().toString();
        if (nameText.isEmpty() || phoneText.isEmpty()) {
            return;
        }

        // Uzyskanie dostępu do bazy danych w trybie zapisu
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Użycie ContentValues do bezpiecznego wstawiania danych
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, nameText);
        values.put(DatabaseHelper.COLUMN_PHONE, phoneText);

        // Wstawienie nowego wiersza
        db.insert(DatabaseHelper.TABLE_NOTES, null, values);
        db.close();

        // Wyczyść pole i odśwież listę
        nameInput.setText("");
        phoneInput.setText("");
        loadNotes();
    }

    private void loadNotes() {
        contactList.clear();
        // Uzyskanie dostępu do bazy danych w trybie odczytu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Zapytanie, które pobierze wszystkie dane z tabeli
        String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PHONE};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NOTES,
                projection,
                null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));
            contactList.add(new Contact(name,phone));
        }
        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();
    }
}