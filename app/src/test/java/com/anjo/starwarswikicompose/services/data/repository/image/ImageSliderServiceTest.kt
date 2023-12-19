package com.anjo.starwarswikicompose.services.data.repository.image

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.data.database.image.ImageSliderDao
import com.anjo.starwarswikicompose.utils.Category
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ImageSliderServiceTest {

    @RelaxedMockK
    lateinit var imageSliderDao: ImageSliderDao

    @InjectMockKs
    lateinit var imageSliderService: ImageSliderService

    @Test
    fun `given imageDao when getimages then return list of imageModel`() = runBlocking {
        //given
        val category = Category.FILMS
        val objectId = "objectId1"
        val expectedList =
            listOf(ImageSliderModel(1, objectId, "url1", category), ImageSliderModel(2, objectId, "url2", category))

        every { imageSliderDao.getImagesForObjectFromRoom(objectId, category) } returns expectedList
        //when
        val actual = imageSliderService.getImagesForObjectFromRoom(objectId, category)

        //then
        actual shouldBe expectedList
    }

    @Test
    fun `given imageSliderModel when invoke addImageToRoomUseCase then verify call`()= runBlocking{
        //given
        val input = ImageSliderModel(null, "objectId", "url.com/objectId", Category.FILMS)

        coEvery { imageSliderDao.addImageToRoom(input) } answers {}

        //when
        imageSliderService.addImageToRoom(input)

        //then
        coVerify { imageSliderDao.addImageToRoom(input) }
    }

    @Test
    fun `given imageSliderModel when invoke deleteImageFromRoomUseCase then verify call`() = runBlocking {
        //given
        val input = ImageSliderModel(null, "objectId", "url.com/objectId", Category.FILMS)

        coEvery { imageSliderDao.deleteImageFromRoom(input) } answers {}

        //when
        imageSliderService.deleteImageFromRoom(input)

        //then
        coVerify { imageSliderDao.deleteImageFromRoom(input) }
    }

}