package com.anjo.starwarswikicompose.presentation.screens.person

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.TestConstants.PEOPLE_NAME
import com.anjo.starwarswikicompose.testutils.assertIncludedImage
import com.anjo.starwarswikicompose.testutils.assertRelatedBoxes
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersonDtoScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val imageSliderUseCases = mockk<ImageSliderUseCases>()
    private val removeUseCase = mockk<DeleteUseCases>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = PersonViewModel(useCase, imageSliderUseCases, removeUseCase, savedStateHandle, dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenPerson_whenDisplayed_thenAsserResults(): Unit = runTest(dispatcher) {
        //given
        val personDto = TestConstants.personDto.copy(id = "objectId1")
        val imagesResponse = listOf(ImageSliderModel(1, personDto.id, "someUrl"))

        coEvery { savedStateHandle.get<String>(DETAILS_PERSON_ARGUMENT_KEY) } returns personDto.id
        coEvery { useCase.getPersonUseCase(personDto.id) } returns personDto
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(personDto.id, PEOPLE) } returns imagesResponse

        viewModel.getPerson()
        advanceUntilIdle()

        val mockNavController = mockk<NavHostController>(relaxed = true)

        composeTestRule.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            PersonScreenContent(state, this, snackBarHostState,
                    imagesStateRefresh, Modifier, mockNavController, viewModel)
        }
        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription(PEOPLE_NAME, useUnmergedTree = true)
        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()
        mainImage.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val mainTitle = composeTestRule.onNodeWithText(personDto.name, useUnmergedTree = true)
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val homeWorld = composeTestRule.onNodeWithText("Home World", useUnmergedTree = true)
        homeWorld.assertIsDisplayed()
        homeWorld.onSibling().assertTextEquals(personDto.homeworld.name)

        val specie = composeTestRule.onNodeWithText("Specie", useUnmergedTree = true)
        specie.assertIsDisplayed()
        specie.onSibling().assertTextEquals(personDto.specie.name)

        val birthYear = composeTestRule.onNodeWithText("Birth Year")
        birthYear.assertIsDisplayed()

        val height = composeTestRule.onNodeWithText("Height", useUnmergedTree = true)
        height.assertIsDisplayed()

        val mass = composeTestRule.onNodeWithText("Mass", useUnmergedTree = true)
        mass.assertIsDisplayed()

        val gender = composeTestRule.onNodeWithText("Gender", useUnmergedTree = true)
        gender.assertIsDisplayed()

        val hair = composeTestRule.onNodeWithText("Hair", useUnmergedTree = true)
        hair.assertIsDisplayed()

        val skin = composeTestRule.onNodeWithText("Skin", useUnmergedTree = true)
        skin.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val moviesBoxTitle = composeTestRule.onNodeWithText(MOVIES_NAME, useUnmergedTree = true)
        moviesBoxTitle.performScrollTo()
        moviesBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, personDto.movieConnection)

        val starshipsBoxTitle = composeTestRule.onNodeWithText(STARSHIPS_NAME, useUnmergedTree = true)
        starshipsBoxTitle.performScrollTo()
        starshipsBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, personDto.starshipConnection)

        val vehiclesBoxTitle = composeTestRule.onNodeWithText(VEHICLES_NAME, useUnmergedTree = true)
        vehiclesBoxTitle.assertIsDisplayed()
        vehiclesBoxTitle.performScrollTo()
        assertRelatedBoxes(composeTestRule, personDto.vehicleConnection)

        assertIncludedImage(composeTestRule)
    }
}