package com.anjo.starwarswikicompose.services.usecases.operationusecase.planet

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
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
class GetAllPlanetsUseCaseTest {
    @MockK
    lateinit var dataFetcher: OperationRepository

    @InjectMockKs
    lateinit var getAllPlanetsUseCase: GetAllPlanetsUseCase

    @Test
    fun `given GetAllPlanetsUseCase when invoke then return list of universal chunk`(): Unit = runTest {
        //given
        val expected = listOf(UniversalChunkDto("id1", "name1", category = PLANETS),
                UniversalChunkDto("id2", "name2", category = PLANETS),
                UniversalChunkDto("id3", "name3", category = PLANETS))
        coEvery { dataFetcher.fetchPlanets() } returns expected

        //when
        val actual = getAllPlanetsUseCase()

        //then
        actual shouldBe expected
    }
}