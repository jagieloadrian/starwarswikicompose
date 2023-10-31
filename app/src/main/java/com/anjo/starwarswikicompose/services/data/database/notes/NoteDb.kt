package com.anjo.starwarswikicompose.services.data.database.notes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anjo.starwarswikicompose.domain.model.NoteModel

@Database(
        entities = [NoteModel::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class NoteDb : RoomDatabase() {
    abstract val notesDao:NotesDao
}