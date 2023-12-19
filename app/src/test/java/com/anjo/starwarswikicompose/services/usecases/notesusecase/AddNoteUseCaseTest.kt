package com.anjo.starwarswikicompose.services.usecases.notesusecase

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.data.repository.notes.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AddNoteUseCaseTest {

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    @InjectMockKs
    lateinit var addNoteUseCase: AddNoteUseCase

    @Test
    fun `given note model when invoke addNoteUseCase then verify call`() =  runBlocking {
        //given
        val noteModel = NoteModel(id = 1, "someText")

        coEvery { notesRepository.addNote(noteModel) } answers {}

        //when
        addNoteUseCase(noteModel)

        //then
        coVerify { notesRepository.addNote(noteModel = noteModel) }
    }
}