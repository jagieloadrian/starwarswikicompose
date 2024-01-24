package com.anjo.starwarswikicompose.presentation.screens.starship.home

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
class HomeStarshipViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var homeStarshipViewModel: HomeStarshipViewModel

    @Test
    fun `given starshipState when getStarships then update starshipState`() = runBlocking {
        //given
        val expected = HomeStarshipViewModel.StarshipState(starships = listOf(), isLoading = true)

        //when
        homeStarshipViewModel.getStarships()
        delay(50)

        val actual = homeStarshipViewModel.fetchedStarships.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given starshipState when fetchStarships then update starshipState`() = runBlocking {
        //given
        val movies = listOf(UniversalChunk("1", "first", "1"), UniversalChunk("2", "second", "2"))
        val expected = HomeStarshipViewModel.StarshipState(starships = movies, isLoading = false)

        coEvery { useCases.getAllStarshipsUseCase() } returns movies

        //when
        homeStarshipViewModel.fetchStarships()
        delay(50)

        val actual = homeStarshipViewModel.fetchedStarships.value

        //then
        actual shouldBe expected
    }
}