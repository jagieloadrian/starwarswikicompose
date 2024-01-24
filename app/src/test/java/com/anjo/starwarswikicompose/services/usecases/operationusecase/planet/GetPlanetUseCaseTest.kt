package com.anjo.starwarswikicompose.services.usecases.operationusecase.planet

import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetPlanetUseCaseTest{
    @MockK
    lateinit var operationRepository: OperationRepository
    @InjectMockKs
    lateinit var getPlanetUseCase: GetPlanetUseCase

    @Test
    fun `given planet when getPlanetUseCase then return planet`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Planet(id = objectId, name = "title")
        coEvery { operationRepository.fetchOnePlanet(objectId) } returns expected

        //when
        val actual = getPlanetUseCase(objectId)

        //then
        actual shouldBe expected
    }
}