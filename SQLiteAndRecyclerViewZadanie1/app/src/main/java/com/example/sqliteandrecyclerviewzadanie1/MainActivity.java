package com.example.sqliteandrecyclerviewzadanie1;

// Implementacja NoteAdapter jest bardzo podobna do UserAdapter z lekcji o RecyclerView
// ... stwórz klasę NoteAdapter dziedziczącą po RecyclerView.Adapter<NoteAdapter.NoteViewHolder> ...
// Pamiętaj o ViewHolderze, onCreateViewHolder, onBindViewHolder i getItemCount```

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
// ... pozostałe importy

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView infoPusta;
    private EditText noteInput;
    private Button saveButton;
    private RecyclerView notesRecyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        infoPusta = findViewById(R.id.infoPusta);
        infoPusta.setVisibility(View.GONE);
        noteInput = findViewById(R.id.noteInput);
        saveButton = findViewById(R.id.saveButton);
        notesRecyclerView = findViewById(R.id.notesRecyclerView);

        // Inicjalizacja listy i adaptera
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList,id->{
            Log.d("id",Long.toString(id));
            deleteNote(id);
        });

        // Ustawienie RecyclerView
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        loadNotes();// Załaduj notatki przy starcie
    }

    private void addNote() {
        String noteText = noteInput.getText().toString();
        if (noteText.isEmpty()) {
            return;
        }

        // Uzyskanie dostępu do bazy danych w trybie zapisu
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Użycie ContentValues do bezpiecznego wstawiania danych
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOTE, noteText);


        // Wstawienie nowego wiersza
        db.insert(DatabaseHelper.TABLE_NOTES, null, values);
        db.close();

        // Wyczyść pole i odśwież listę
        noteInput.setText("");
        loadNotes(); // Po dodaniu, odśwież listę
    }

    private void loadNotes() {
        noteList.clear(); // Wyczyść starą listę przed wczytaniem nowej
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_NOTES,
                null, null, null, null, null, null);

        // Pętla po cursorze i dodawanie notatek do listy obiektów
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTE));
            noteList.add(new Note(id, text));
        }
        cursor.close();
        db.close();

        // Poinformuj adapter, że dane się zmieniły
        adapter.notifyDataSetChanged();

        if(noteList.isEmpty()){
            notesRecyclerView.setVisibility(View.GONE);
            infoPusta.setVisibility(View.VISIBLE);
        }
        if(!noteList.isEmpty()){
            notesRecyclerView.setVisibility(View.VISIBLE);
            infoPusta.setVisibility(View.GONE);
        }
    }

    private  void deleteNote(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DatabaseHelper.TABLE_NOTES,DatabaseHelper.COLUMN_ID + " = ?", new String[]{Long.toString(id)});

        loadNotes();
    }
}