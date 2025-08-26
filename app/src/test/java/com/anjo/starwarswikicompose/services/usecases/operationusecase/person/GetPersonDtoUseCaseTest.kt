package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.starwarswikicompose.domain.dto.PersonDto
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
class GetPersonDtoUseCaseTest {

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var getPersonUseCase: GetPersonUseCase

    @Test
    fun `given person when getPersonUseCase then return person`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = PersonDto(id = objectId, name = "title")
        coEvery { operationRepository.fetchOnePerson(objectId) } returns expected

        //when
        val actual = getPersonUseCase(objectId)

        //then
        actual shouldBe expected
    }
}