package com.anjo.starwarswikicompose.presentation.screens.planet.home

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
class HomePlanetViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var homePlanetViewModel: HomePlanetViewModel

    @Test
    fun `given planetState when getPlanets then update planetState`(): Unit = runBlocking {
        //given
        val expected = HomePlanetViewModel.PlanetState(planets = listOf(), isLoading = true)

        //when
        homePlanetViewModel.getPlanets()
        delay(50)

        val actual = homePlanetViewModel.fetchedPlanets.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given planetState when fetchPlanets then update planetState`(): Unit = runBlocking {
        //given
        val objects = listOf(UniversalChunk("1", "first", "1"), UniversalChunk("2", "second", "2"))
        val expected = HomePlanetViewModel.PlanetState(planets = objects, isLoading = false)

        coEvery { useCases.getAllPlanetsUseCase() } returns objects

        //when
        homePlanetViewModel.fetchPlanets()
        delay(50)

        val actual = homePlanetViewModel.fetchedPlanets.value

        //then
        actual shouldBe expected
    }
}