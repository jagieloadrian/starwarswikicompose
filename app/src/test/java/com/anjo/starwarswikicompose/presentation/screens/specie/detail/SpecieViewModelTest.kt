package com.anjo.starwarswikicompose.presentation.screens.specie.detail

import androidx.lifecycle.SavedStateHandle
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.SpecieDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_SPECIE_ARGUMENT_KEY
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SpecieViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @RelaxedMockK
    private lateinit var imageSliderUseCases: ImageSliderUseCases

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle

    @InjectMockKs
    private lateinit var specieViewModel: SpecieViewModel

    @Test
    fun `given specie when getSpecie then return specie and related images`() = runBlocking {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", SPECIES)
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2", SPECIES)
        val expectedImages = listOf(imageSliderModel, imageSliderModel2)
        val expected = SpecieDetailState(specie = Specie(id = objectId, "Title", classification = "1", designation = "mass"),
                state = DetailObjectState.SUCCESS)

        coEvery { savedStateHandle.get<String>(DETAILS_SPECIE_ARGUMENT_KEY) } returns objectId
        coEvery { useCases.getSpecieUseCase(objectId) } returns expected.specie
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, SPECIES) } returns expectedImages

        //when
        specieViewModel.getSpecie()
        delay(2050)
        val actual = specieViewModel.selectedSpecie.value
        val actualImages = specieViewModel.images.value

        //then
        actual shouldBe expected
        actualImages shouldBe expectedImages
    }

    @Test
    fun `given objectId and photoUrl when saveInDatabase then verify call`() = runBlocking {
        //given
        val objectId = "objectId"
        val photoUrl = "photoUrl"
        val expected = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = SPECIES)
        val slot = slot<ImageSliderModel>()

        //when
        specieViewModel.saveInDatabase(objectId, photoUrl)
        delay(50)

        //then
        coVerify { imageSliderUseCases.addImageToRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe expected
    }

    @Test
    fun `given Images when refreshImages then return list of images`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", SPECIES)
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2", SPECIES)
        val expected = listOf(imageSliderModel, imageSliderModel2)

        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, SPECIES) } returns expected

        //when
        specieViewModel.refreshImages(objectId)
        delay(50)
        val actual = specieViewModel.images.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given ImageSliderModel when deleteFromDatabase then verify call`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", SPECIES)
        val slot = slot<ImageSliderModel>()

        //when
        specieViewModel.deleteFromDatabase(imageSliderModel)
        delay(50)

        //then
        coVerify { imageSliderUseCases.deleteImageFromRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe imageSliderModel
    }
}