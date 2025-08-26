package com.anjo.starwarswikicompose.services.data.repository.notes

import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.services.data.database.notes.NotesDao
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class NotesServiceTest {

    @RelaxedMockK
    private lateinit var notesDao: NotesDao

    @InjectMockKs
    lateinit var notesService: NotesService

    @Test
    fun `given notesDao when getNotes then return list of noteModel`() = runTest {
        //given
        val expectedList = listOf(NoteModel(1, "someText1"), NoteModel(2, "someText2"), NoteModel(3, "someText3"))

        coEvery { notesDao.getNotes() } returns flow { emit(expectedList) }
        //when
        val actual = notesService.getNotes().first()

        //then
        actual shouldBe expectedList
    }

    @Test
    fun `given note model when  deleteNote then verify call`() = runTest {
        //given
        val noteModel = NoteModel(id = 1, "someText")

        coEvery { notesDao.deleteNote(noteModel) } answers {}

        //when
        notesService.deleteNote(noteModel)

        //then
        coVerify { notesDao.deleteNote(noteModel = noteModel) }
    }

    @Test
    fun `given note model when addNote then verify call`() = runTest {
        //given
        val noteModel = NoteModel(id = 1, "someText")

        coEvery { notesDao.addNote(noteModel) } answers {}

        //when
        notesService.addNote(noteModel)

        //then
        coVerify { notesDao.addNote(noteModel = noteModel) }
    }
}