package com.anjo.starwarswikicompose.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.usecases.notesusecase.NotesUseCases
import com.anjo.starwarswikicompose.utils.Constants.DEFAULT_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CardNoteViewModel @Inject constructor(
        private val notesUseCases: NotesUseCases,
) : ViewModel() {
    private var _notes: MutableStateFlow<NoteModel> = MutableStateFlow(NoteModel())
    open val note: StateFlow<NoteModel> = _notes

    fun updateNote(newText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = NoteModel(
                    text = newText
            )
            notesUseCases.addNoteUseCase(model)
        }
    }

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.getNotesUseCase().collect {
                if (it.isEmpty()) {
                    _notes.value = NoteModel(text = DEFAULT_VALUE)
                } else {
                    _notes.value = it.maxBy { noteModel -> noteModel.lastChanged }
                }
            }
        }
    }
}