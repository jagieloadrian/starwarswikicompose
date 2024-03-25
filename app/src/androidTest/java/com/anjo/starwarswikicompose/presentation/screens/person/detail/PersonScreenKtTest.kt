package com.anjo.starwarswikicompose.presentation.screens.person.detail

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.Unit.CM
import com.anjo.starwarswikicompose.domain.model.Unit.KG
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(AndroidJUnit4::class)
class PersonScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navHostController: NavHostController
    private lateinit var personViewModel: PersonViewModel

    @Test
    fun givenPerson_whenDisplayed_thenAsserResults(): Unit = runBlocking {
        //given
        val person = Person(id = "objectId1", name = "title", birthYear = "1", height = "height",
                mass = "it was", gender = "gender", hair = "hairs", skin = "skins",
                homeworld = UniversalChunk(id = "planetId", name = "planetName"),
                specie = UniversalChunk(id = "specieId", name = "specieName"),
                vehicleConnection = Connection(1, listOf(UniversalChunk(id = "vehicleId", name = "vehicleName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "movieId", name = "movieName"))),
                starshipConnection = Connection(1, listOf(UniversalChunk(id = "starshipId", name = "StarshipName"))))

        personViewModel = Mockito.mock(PersonViewModel::class.java)

        val imagesResponse = listOf(ImageSliderModel(1, person.id, "someUrl"))
        lateinit var imagesFLow: StateFlow<List<ImageSliderModel>>
        val scope = CoroutineScope(coroutineContext)
        flow { emit(imagesResponse) }.stateIn(scope).also { imagesFLow = it }

        personViewModel.stub {
            on { images } doReturn imagesFLow
        }
        composeTestRule.setContent {
            navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            PersonScreenContent(state, scope, snackBarHostState,
                    imagesStateRefresh, Modifier, navHostController, person, personViewModel)
        }
        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription("PEOPLE")

        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()

        val mainTitle = composeTestRule.onNodeWithText("title")
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val homeWorld = composeTestRule.onNodeWithText("Home World", useUnmergedTree = true)
        homeWorld.assertIsDisplayed()
        homeWorld.onSibling().assertTextEquals(person.homeworld.name)

        val specie = composeTestRule.onNodeWithText("Specie", useUnmergedTree = true)
        specie.assertIsDisplayed()
        specie.onSibling().assertTextEquals(person.specie.name)

        val birthYear = composeTestRule.onNodeWithText("Birth Year")
        birthYear.assertIsDisplayed()

        val height = composeTestRule.onNodeWithText("Height", useUnmergedTree = true)
        height.assertIsDisplayed()
        height.onSibling().assertTextEquals("${person.height} ${CM.description}")

        val mass = composeTestRule.onNodeWithText("Mass", useUnmergedTree = true)
        mass.assertIsDisplayed()
        mass.onSibling().assertTextEquals("${person.mass} ${KG.description}")

        val gender = composeTestRule.onNodeWithText("Gender", useUnmergedTree = true)
        gender.assertIsDisplayed()
        gender.onSibling().assertTextEquals(person.gender)

        val hair = composeTestRule.onNodeWithText("Hair", useUnmergedTree = true)
        hair.assertIsDisplayed()
        hair.onSibling().assertTextEquals(person.hair)

        val skin = composeTestRule.onNodeWithText("Skin", useUnmergedTree = true)
        skin.assertIsDisplayed()
        skin.onSibling().assertTextEquals(person.skin)


        val characterBox = composeTestRule.onNodeWithContentDescription("RelatedBox movieName", useUnmergedTree = true)
        characterBox.assertIsDisplayed()
        characterBox.assertIsEnabled()
        characterBox.onSibling().assertTextEquals(person.movieConnection.objects[0].name)

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val starshipBox = composeTestRule.onNodeWithContentDescription("RelatedBox StarshipName", useUnmergedTree = true)
        starshipBox.assertIsDisplayed()
        starshipBox.assertIsEnabled()
        starshipBox.onSibling().assertTextEquals(person.starshipConnection.objects[0].name)

            val vehicleBox = composeTestRule.onNodeWithContentDescription("RelatedBox vehicleName", useUnmergedTree = true)
        vehicleBox.assertIsDisplayed()
        vehicleBox.assertIsEnabled()
        vehicleBox.onSibling().assertTextEquals(person.vehicleConnection.objects[0].name)

        val image = composeTestRule.onNodeWithContentDescription("image from flickr",
                substring = true,
                ignoreCase = true,
                useUnmergedTree = true)

        image.assertIsDisplayed()
        image.assertIsEnabled()

        val onClickLeft = composeTestRule.onNodeWithContentDescription("onClickLeft", useUnmergedTree = true)
        onClickLeft.assertIsDisplayed()
        onClickLeft.assertIsEnabled()
        onClickLeft.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickLeft.onParent().assertIsNotFocused()

        val onClickRight = composeTestRule.onNodeWithContentDescription("onCLickRight",  useUnmergedTree = true)
        onClickRight.assertIsDisplayed()
        onClickRight.assertIsEnabled()
        onClickRight.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickRight.onParent().assertIsNotFocused()

    }
}