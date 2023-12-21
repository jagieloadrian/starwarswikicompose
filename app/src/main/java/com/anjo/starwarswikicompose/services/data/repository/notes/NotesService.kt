package com.anjo.starwarswikicompose.services.data.repository.notes

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.data.database.notes.NotesDao
import kotlinx.coroutines.flow.Flow

class NotesService(private val notesDao: NotesDao) : NotesRepository {
    override fun getNotes(): Flow<List<NoteModel>> {
        return notesDao.getNotes()
    }

    override suspend fun addNote(noteModel: NoteModel) {
        return notesDao.addNote(noteModel)
    }

    override suspend fun deleteNote(noteModel: NoteModel) {
        return notesDao.deleteNote(noteModel)
    }
}