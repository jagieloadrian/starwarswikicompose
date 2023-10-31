package com.anjo.starwarswikicompose.services.data.repository.notes

import com.anjo.starwarswikicompose.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<NoteModel>>

    suspend fun addNote(noteModel: NoteModel)

    suspend fun deleteNote(noteModel: NoteModel)
}