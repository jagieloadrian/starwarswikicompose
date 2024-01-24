package com.anjo.starwarswikicompose.services.usecases.operationusecase.movie

import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
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
class GetAllFilmsUseCaseTest {
    @MockK
    lateinit var dataFetcher: OperationRepository

    @InjectMockKs
    lateinit var getAllFilmsUseCase: GetAllFilmsUseCase

    @Test
    fun `given GetAllFilmsUseCase when invoke then return list of universal chunk`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk("id1", "name1"),
                UniversalChunk("id2", "name2"),
                UniversalChunk("id3", "name3"))
        coEvery { dataFetcher.fetchFilms() } returns expected

        //when
        val actual = getAllFilmsUseCase()

        //then
        actual shouldBe expected
    }

}