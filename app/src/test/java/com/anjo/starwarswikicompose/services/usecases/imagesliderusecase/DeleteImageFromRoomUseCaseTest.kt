package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeleteImageFromRoomUseCaseTest {

    @RelaxedMockK
    private lateinit var imageSliderRepository: ImageSliderRepository

    @InjectMockKs
    lateinit var deleteImageFromRoomUseCase: DeleteImageFromRoomUseCase

    @Test
    fun `given imageSliderModel when invoke deleteImageFromRoomUseCase then verify call`() = runBlocking {
        //given
        val input = ImageSliderModel(null, "objectId", "url.com/objectId", Category.FILMS)

        coEvery { imageSliderRepository.deleteImageFromRoom(input) } answers {}

        //when
        deleteImageFromRoomUseCase(input)

        //then
        coVerify { imageSliderRepository.deleteImageFromRoom(input) }
    }
}