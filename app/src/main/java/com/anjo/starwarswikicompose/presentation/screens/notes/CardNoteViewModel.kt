package com.anjo.starwarswikicompose.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.usecases.notesusecase.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
open class CardNoteViewModel @Inject constructor(
        private val notesUseCases: NotesUseCases,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private var _notes: MutableStateFlow<NoteModel> = MutableStateFlow(NoteModel())
    open val note: StateFlow<NoteModel> = _notes

    fun updateNote(newText: String) {
        viewModelScope.launch(ioDispatcher) {
            val model = NoteModel(
                    text = newText
            )
            notesUseCases.addNoteUseCase(model)
        }
    }

    fun getNotes() {
        viewModelScope.launch(ioDispatcher) {
            notesUseCases.getNotesUseCase().collect { notes ->
                if (notes.isEmpty()) {
                    _notes.update {
                        NoteModel(text = "")
                    }
                } else {
                    _notes.update {
                        notes.maxBy { noteModel -> noteModel.lastChanged }
                    }
                }
            }
        }
    }
}