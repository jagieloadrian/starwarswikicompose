package com.anjo.starwarswikicompose.services.usecases.notesusecase

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.data.repository.notes.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
        private val notesRepository: NotesRepository,
) {

    suspend operator fun invoke(noteModel: NoteModel) {
    notesRepository.deleteNote(noteModel)
    }
}