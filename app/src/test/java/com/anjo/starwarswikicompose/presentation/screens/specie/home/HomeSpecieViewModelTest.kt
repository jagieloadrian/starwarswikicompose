package com.anjo.starwarswikicompose.presentation.screens.specie.home

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
class HomeSpecieViewModelTest{

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var homeSpecieViewModel: HomeSpecieViewModel

    @Test
    fun `given specieState when getSpecies then update specieState`(): Unit = runBlocking {
        //given
        val expected = HomeSpecieViewModel.SpecieState(species = listOf(), isLoading = true)

        //when
        homeSpecieViewModel.getSpecies()
        delay(50)

        val actual = homeSpecieViewModel.fetchedSpecies.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given specieState when fetchedSpecies then update specieState`(): Unit = runBlocking {
        //given
        val movies = listOf(UniversalChunk("1", "first", "1"), UniversalChunk("2", "second", "2"))
        val expected = HomeSpecieViewModel.SpecieState(species = movies, isLoading = false)

        coEvery { useCases.getAllSpeciesUseCase() } returns movies

        //when
        homeSpecieViewModel.fetchSpecies()
        delay(50)

        val actual = homeSpecieViewModel.fetchedSpecies.value

        //then
        actual shouldBe expected
    }
}