package com.anjo.starwarswikicompose.services.usecases.notesusecase

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.data.repository.notes.NotesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase @Inject constructor(
        private val notesRepository: NotesRepository,
) {
    operator fun invoke(): Flow<List<NoteModel>> {
        return notesRepository.getNotes()
    }
}