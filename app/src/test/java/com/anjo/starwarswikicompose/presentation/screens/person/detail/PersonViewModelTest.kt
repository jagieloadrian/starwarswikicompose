package com.anjo.starwarswikicompose.presentation.screens.person.detail

import androidx.lifecycle.SavedStateHandle
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.DetailObjectState.SUCCESS
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.PersonDetailState
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
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
class PersonViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @RelaxedMockK
    private lateinit var imageSliderUseCases: ImageSliderUseCases

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle

    @InjectMockKs
    private lateinit var personViewModel: PersonViewModel

    @Test
    fun `given person when getPerson then return person and related images`() = runBlocking {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", PEOPLE)
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2", PEOPLE)
        val expectedImages = listOf(imageSliderModel, imageSliderModel2)
        val expected = PersonDetailState(person = Person(id = objectId, "Title", birthYear = "1",  mass = "mass"),
                state = SUCCESS)

        coEvery { savedStateHandle.get<String>(DETAILS_PERSON_ARGUMENT_KEY) } returns objectId
        coEvery { useCases.getPersonUseCase(objectId) } returns expected.person
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, PEOPLE) } returns expectedImages

        //when
        personViewModel.getPerson()
        delay(2050)
        val actual = personViewModel.selectedPerson.value
        val actualImages = personViewModel.images.value

        //then
        actual shouldBe expected
        actualImages shouldBe expectedImages
    }

    @Test
    fun `given objectId and photoUrl when saveInDatabase then verify call`() = runBlocking {
        //given
        val objectId = "objectId"
        val photoUrl = "photoUrl"
        val expected = ImageSliderModel(objectId = objectId, url = photoUrl, objectType = PEOPLE)
        val slot = slot<ImageSliderModel>()

        //when
        personViewModel.saveInDatabase(objectId, photoUrl)
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
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl")
        val imageSliderModel2 = ImageSliderModel(2, objectId, "someUrl2")
        val expected = listOf(imageSliderModel, imageSliderModel2)

        coEvery { imageSliderUseCases.getImagesForObjectUseCase(objectId, PEOPLE) } returns expected

        //when
        personViewModel.refreshImages(objectId)
        delay(50)
        val actual = personViewModel.images.value

        //then
        actual shouldBe expected
    }

    @Test
    fun `given ImageSliderModel when deleteFromDatabase then verify call`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val imageSliderModel = ImageSliderModel(1, objectId, "someUrl", PEOPLE)
        val slot = slot<ImageSliderModel>()

        //when
        personViewModel.deleteFromDatabase(imageSliderModel)
        delay(50)

        //then
        coVerify { imageSliderUseCases.deleteImageFromRoomUseCase(capture(slot)) }
        val actual = slot.captured
        actual shouldBe imageSliderModel
    }
}