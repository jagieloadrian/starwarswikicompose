@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.planet

import androidx.lifecycle.SavedStateHandle
import com.anjo.starwarswikicompose.domain.dto.PlanetDetailState
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PlanetViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @RelaxedMockK
    private lateinit var imageSliderUseCases: ImageSliderUseCases

    @RelaxedMockK
    private lateinit var deleteUseCases: DeleteUseCases

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @InjectMockKs
    private lateinit var planetViewModel: PlanetViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given planet when getPlanet then return planet and related images`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", PLANETS)
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2", PLANETS)
        val expectedImages = listOf(imageSliderModel, imageSliderModel2)
        val expected =
            PlanetDetailState(planetDto = PlanetDto(id = objectId, "Title", population = "1", gravity = "mass"),
                    state = DetailObjectState.SUCCESS)

        coEvery { savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY) } returns objectId
        coEvery { useCases.getPlanetUseCase(objectId) } returns expected.planetDto
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, PLANETS) } returns expectedImages

        //when
        planetViewModel.getPlanet()
        advanceUntilIdle()
        val actual = planetViewModel.selectedPlanet.value
        val actualImages = planetViewModel.images.value

        //then
        actual shouldBe expected
        actualImages shouldBe expectedImages
    }

    @Test
    fun `given objectId and photoUrl when saveInDatabase then verify call`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val photoUrl = "photoUrl"
        val expected = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = PLANETS)
        val slot = slot<ImageSliderModel>()

        //when
        planetViewModel.saveInDatabase(objectId, photoUrl)
        advanceUntilIdle()

        //then
        coVerify { imageSliderUseCases.addImageToRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe expected
    }

    @Test
    fun `given Images when refreshImages then return list of images`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl")
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2")
        val expected = listOf(imageSliderModel, imageSliderModel2)

        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, PLANETS) } returns expected

        //when
        planetViewModel.refreshImages(objectId)
        advanceUntilIdle()
        val actual = planetViewModel.images.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given ImageSliderModel when deleteFromDatabase then verify call`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", PLANETS)
        val slot = slot<ImageSliderModel>()

        //when
        planetViewModel.deleteFromDatabase(imageSliderModel)
        advanceUntilIdle()

        //then
        coVerify { imageSliderUseCases.deleteImageFromRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe imageSliderModel
    }
}