package com.anjo.starwarswikicompose.services.data.database.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.utils.Constants.NOTES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM $NOTES_TABLE")
    fun getNotes(): Flow<List<NoteModel>>

    @Insert(onConflict = REPLACE)
    suspend fun addNote(noteModel: NoteModel)

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)
}