package com.example.alarmclockreminder.dao;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.alarmclockreminder.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertNote(Note note);

    @Delete
    void  deleteNote(Note note);
}
