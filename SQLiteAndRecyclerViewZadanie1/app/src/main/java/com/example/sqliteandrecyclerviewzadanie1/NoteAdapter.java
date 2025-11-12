package com.example.sqliteandrecyclerviewzadanie1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    // Ta metoda tworzy nowy ViewHolder, gdy RecyclerView go potrzebuje.
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note, parent, false);
        return new NoteViewHolder(view);
    }

    // Ta metoda wypełnia ViewHolder danymi dla określonej pozycji na liście.
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = noteList.get(position);
        holder.id.setText(currentNote.getId());
        holder.Text.setText(currentNote.getText());
    }

    // Ta metoda zwraca liczbę elementów na liście.
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // Wewnętrzna klasa ViewHolder przechowuje referencje do widoków wiersza.
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView Text,id;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idText);
            Text = itemView.findViewById(R.id.noteText);
        }
    }
}
