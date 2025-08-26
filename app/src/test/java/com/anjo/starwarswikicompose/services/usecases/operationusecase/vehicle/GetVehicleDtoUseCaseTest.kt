package com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle

import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetVehicleDtoUseCaseTest {
    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var getVehicleUseCase: GetVehicleUseCase

    @Test
    fun `given film vehicle when getVehicleUseCase then return vehicle`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = VehicleDto(id = objectId, name = "title")
        coEvery { operationRepository.fetchOneVehicle(objectId) } returns expected

        //when
        val actual = getVehicleUseCase(objectId)

        //then
        actual shouldBe expected
    }
}