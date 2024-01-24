package com.anjo.starwarswikicompose.presentation.screens.movie.home

import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HomeMovieViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var homeMovieViewModel: HomeMovieViewModel

    @Test
    fun `given movieState when getMovies then update movieState`() = runBlocking {
        //given
        val expected = HomeMovieViewModel.MovieState(movies = listOf(), isLoading = true)

        //when
        homeMovieViewModel.getMovies()
        delay(50)

        val actual = homeMovieViewModel.fetchedFilms.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given movieState when fetchFilms then update movieState`() = runBlocking {
        //given
        val movies = listOf(UniversalChunk("1", "first", "1"), UniversalChunk("2", "second", "2"))
        val expected = HomeMovieViewModel.MovieState(movies = movies, isLoading = false)

        coEvery { useCases.getAllFilmsUseCase() } returns movies

        //when
        homeMovieViewModel.fetchFilms()
        delay(50)

        val actual = homeMovieViewModel.fetchedFilms.value

        //then
        actual shouldBe expected
    }
}