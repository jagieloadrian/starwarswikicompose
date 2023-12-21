package com.anjo.starwarswikicompose.services.usecases.notesusecase

data class NotesUseCases(
        val addNoteUseCase: AddNoteUseCase,
        val deleteNoteUseCase: DeleteNoteUseCase,
        val getNotesUseCase: GetNotesUseCase,
)