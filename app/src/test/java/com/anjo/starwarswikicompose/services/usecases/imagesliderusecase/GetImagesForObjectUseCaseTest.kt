package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)
class GetImagesForObjectUseCaseTest {

    @RelaxedMockK
    private lateinit var imageSliderRepository: ImageSliderRepository

    @InjectMockKs
    lateinit var getImagesForObjectUseCase: GetImagesForObjectUseCase

    @Test
    fun `given imageRepo when invoke getImagesForObjectUseCase then return list of imageModel`() = runBlocking {
        //given
        val category = Category.FILMS
        val objectId = "objectId1"
        val expectedList =
            listOf(ImageSliderModel(1, objectId, "url1", category), ImageSliderModel(2, objectId, "url2", category))

        every { imageSliderRepository.getImagesForObjectFromRoom(objectId, category) } returns expectedList
        //when
        val actual = getImagesForObjectUseCase(objectId, category)

        //then
        actual shouldBe expectedList
    }
}