package com.anjo.starwarswikicompose.services.di

import android.content.Context
import androidx.room.Room
import com.anjo.starwarswikicompose.services.data.database.notes.NoteDb
import com.anjo.starwarswikicompose.services.data.database.notes.NotesDao
import com.anjo.starwarswikicompose.services.data.repository.notes.NotesRepository
import com.anjo.starwarswikicompose.services.data.repository.notes.NotesService
import com.anjo.starwarswikicompose.services.usecases.notesusecase.AddNoteUseCase
import com.anjo.starwarswikicompose.services.usecases.notesusecase.DeleteNoteUseCase
import com.anjo.starwarswikicompose.services.usecases.notesusecase.GetNotesUseCase
import com.anjo.starwarswikicompose.services.usecases.notesusecase.NotesUseCases
import com.anjo.starwarswikicompose.utils.Constants.NOTES_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteRepositoryModule {

    @Provides
    @Singleton
    fun provideNoteDb(
            @ApplicationContext
            context: Context,
    ) = Room.databaseBuilder(
            context,
            NoteDb::class.java,
            NOTES_TABLE).build()

    @Provides
    @Singleton
    fun provideNotesDao(
            noteDb: NoteDb,
    ) = noteDb.notesDao

    @Provides
    @Singleton
    fun provideNotesRepository(
            notesDao: NotesDao,
    ): NotesRepository {
        return NotesService(notesDao = notesDao)
    }

    @Provides
    @Singleton
    fun provideSliderUseCases(notesRepository: NotesRepository): NotesUseCases {
        return NotesUseCases(
                addNoteUseCase = AddNoteUseCase(notesRepository),
                deleteNoteUseCase = DeleteNoteUseCase(notesRepository),
                getNotesUseCase = GetNotesUseCase(notesRepository))
    }
}