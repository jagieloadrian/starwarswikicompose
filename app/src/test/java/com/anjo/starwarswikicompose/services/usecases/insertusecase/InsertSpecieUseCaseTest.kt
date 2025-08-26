package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.SpecieDto
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
class InsertSpecieUseCaseTest {

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var insertUseCase: InsertSpecieUseCase

    @Test
    fun `given arg when insertUseCase then verify call`() = runTest {
        //given
        val dto = SpecieDto("id1", "name1")
        val expected = "new_id"
        coEvery { operationRepository.insertSpecie(dto) } returns expected

        //when
        val actual = insertUseCase(dto)

        //then
        actual shouldBe expected
    }

}