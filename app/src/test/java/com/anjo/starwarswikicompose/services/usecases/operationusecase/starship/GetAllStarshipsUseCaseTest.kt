package com.anjo.starwarswikicompose.services.usecases.operationusecase.starship

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
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
class GetAllStarshipsUseCaseTest {
    @MockK
    lateinit var dataFetcher: OperationRepository

    @InjectMockKs
    lateinit var getAllStarshipsUseCase: GetAllStarshipsUseCase

    @Test
    fun `given GetAllStarshipsUseCase when invoke then return list of universal chunk`(): Unit = runTest {
        //given
        val expected = listOf(UniversalChunkDto("id1", "name1", category = STARSHIPS),
                UniversalChunkDto("id2", "name2", category = STARSHIPS),
                UniversalChunkDto("id3", "name3", category = STARSHIPS))
        coEvery { dataFetcher.fetchStarships() } returns expected

        //when
        val actual = getAllStarshipsUseCase()

        //then
        actual shouldBe expected
    }
}