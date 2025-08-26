package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.mapper.toModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
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
class InsertChunkUseCaseTest {

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var insertChunkUseCase: InsertChunkUseCase

    @Test
    fun `given arg when insertChunkUseCase then verify call`() = runTest {
        //given
        val dto = UniversalChunkDto("id1", "name1", category = Category.PLANETS)
        val expected = "new_id"
        coEvery { operationRepository.insertChunk(dto.toModel()) } returns expected

        //when
        val actual = insertChunkUseCase(dto)

        //then
        actual shouldBe expected
    }

}