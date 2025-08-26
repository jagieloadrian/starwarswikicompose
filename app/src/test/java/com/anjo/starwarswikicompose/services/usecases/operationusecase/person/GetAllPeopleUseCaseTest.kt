package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
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
class GetAllPeopleUseCaseTest {
    @MockK
    lateinit var dataFetcher: OperationRepository

    @InjectMockKs
    lateinit var getAllPeopleUseCase: GetAllPeopleUseCase

    @Test
    fun `given GetAllPeopleUseCase when invoke then return list of universal chunk`(): Unit = runTest {
        //given
        val expected = listOf(UniversalChunkDto("id1", "name1", category = PEOPLE),
                UniversalChunkDto("id2", "name2", category = PEOPLE),
                UniversalChunkDto("id3", "name3", category = PEOPLE))
        coEvery { dataFetcher.fetchPeoples() } returns expected

        //when
        val actual = getAllPeopleUseCase()

        //then
        actual shouldBe expected
    }
}