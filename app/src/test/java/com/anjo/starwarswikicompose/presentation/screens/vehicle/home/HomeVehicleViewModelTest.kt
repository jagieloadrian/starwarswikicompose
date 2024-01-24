package com.anjo.starwarswikicompose.presentation.screens.vehicle.home

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
class HomeVehicleViewModelTest{

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @InjectMockKs
    private lateinit var homeVehicleViewModel: HomeVehicleViewModel

    @Test
    fun `given vehicleState when getVehicles then update vehicleState`() = runBlocking {
        //given
        val expected = HomeVehicleViewModel.VehicleState(vehicles = listOf(), isLoading = true)

        //when
        homeVehicleViewModel.getVehicles()
        delay(50)

        val actual = homeVehicleViewModel.fetchedVehicles.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given vehicleState when fetchVehicles then update vehicleState`() = runBlocking {
        //given
        val movies = listOf(UniversalChunk("1", "first", "1"), UniversalChunk("2", "second", "2"))
        val expected = HomeVehicleViewModel.VehicleState(vehicles = movies, isLoading = false)

        coEvery { useCases.getAllVehicleUseCase() } returns movies

        //when
        homeVehicleViewModel.fetchVehicles()
        delay(50)

        val actual = homeVehicleViewModel.fetchedVehicles.value

        //then
        actual shouldBe expected
    }
}