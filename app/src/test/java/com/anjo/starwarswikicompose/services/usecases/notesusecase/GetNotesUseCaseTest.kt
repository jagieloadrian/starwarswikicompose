package com.anjo.starwarswikicompose.services.usecases.notesusecase

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.data.repository.notes.NotesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetNotesUseCaseTest {

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    @InjectMockKs
    lateinit var getNotesUseCase: GetNotesUseCase

    @Test
    fun `given notesRepo when invoke getNotesUseCase then return list of noteModel`() = runBlocking {
        //given
        val expectedList = listOf(NoteModel(1, "someText1"), NoteModel(2, "someText2"), NoteModel(3, "someText3"))

        coEvery { notesRepository.getNotes() } returns flow { emit(expectedList) }
        //when
        val actual = getNotesUseCase().firstOrNull()

        //then
        actual!! shouldBe expectedList
    }
}