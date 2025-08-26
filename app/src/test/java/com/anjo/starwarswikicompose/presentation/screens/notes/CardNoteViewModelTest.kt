@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.notes

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.usecases.notesusecase.NotesUseCases
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.temporal.ChronoUnit

@ExtendWith(MockKExtension::class)
class CardNoteViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: NotesUseCases
    private val testDispatcher = StandardTestDispatcher()

    @InjectMockKs
    private lateinit var cardNoteViewModel: CardNoteViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given cardNoteViewModel when getNotes then return empty object`() {
        //given
        val expected = NoteModel()

        //when
        val actual = cardNoteViewModel.note.value

        //then
        actual.id shouldBe expected.id
        actual.text shouldBe expected.text
        actual.lastChanged.truncatedTo(ChronoUnit.SECONDS) shouldBe expected.lastChanged.truncatedTo(ChronoUnit.SECONDS)
    }

    @Test
    fun `given useCase when getNotes then return expected object`() = runTest {
        //given
        val expected = NoteModel(id = 1, text = "ExampleTest")

        coEvery { useCases.getNotesUseCase() } returns flow { emit(listOf(expected)) }

        //when
        cardNoteViewModel.getNotes()
        advanceUntilIdle()

        val actual = cardNoteViewModel.note.value

        //then
        actual.id shouldBe expected.id
        actual.text shouldBe expected.text
        actual.lastChanged.truncatedTo(ChronoUnit.SECONDS) shouldBe expected.lastChanged.truncatedTo(ChronoUnit.SECONDS)
    }

    @Test
    fun `given useCase with empty list when getNotes then return object with default text`() = runTest {
        //given
        coEvery { useCases.getNotesUseCase() } returns flow { emit(listOf()) }

        //when
        cardNoteViewModel.getNotes()
        val actual = cardNoteViewModel.note.value

        //then
        actual.text shouldBe ""
    }

    @Test
    fun `given useCase and Text when updateNote then return object with new text`() = runTest {
        //given
        val updateText = "New Updated Text"
        val slot = slot<NoteModel>()

        coEvery { useCases.addNoteUseCase(capture(slot)) } answers { }

        //when
        cardNoteViewModel.updateNote(updateText)
        advanceUntilIdle()

        //then
        slot.captured.text shouldBe updateText
    }
}