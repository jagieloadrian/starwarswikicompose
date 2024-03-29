package com.anjo.starwarswikicompose.presentation.screens.starship.detail

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
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.UnitName.CLASS
import com.anjo.starwarswikicompose.domain.model.UnitName.CREDITS
import com.anjo.starwarswikicompose.domain.model.UnitName.KG
import com.anjo.starwarswikicompose.domain.model.UnitName.KMPERHOUR
import com.anjo.starwarswikicompose.domain.model.UnitName.M
import com.anjo.starwarswikicompose.domain.model.UnitName.MEN
import com.anjo.starwarswikicompose.domain.model.UnitName.PERHOUR
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Starship
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
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(AndroidJUnit4::class)
class StarshipScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var starshipViewModel: StarshipViewModel


    @Test
    fun givenStarshipObject_whenDisplayScreen_thenAssertOps(): Unit = runBlocking {
        //given
        val starship = Starship(id = "objectId1", name = "title", model = "1", starshipClass = "height",
                cost = "it was", length = "gender", cargoCapacity = "hairs",
                manufacturers = listOf("skins", "skulls"), vMax = "water", hyperdriveRating = "ground",
                megalight = "light", crew = "a lot", passengers = "pilot", consumables = "it eats a lot",
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "movieId", name = "movieName")))
        )

        starshipViewModel = mock(StarshipViewModel::class.java)

        val imagesResponse = listOf(ImageSliderModel(1, starship.id, "someUrl"))
        lateinit var imagesFLow: StateFlow<List<ImageSliderModel>>
        val scope = CoroutineScope(coroutineContext)
        flow { emit(imagesResponse) }.stateIn(scope).also { imagesFLow = it }

        starshipViewModel.stub {
            on { images } doReturn imagesFLow
        }
        composeTestRule.setContent {
            val navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            StarshipContentScreen(state, scope, snackBarHostState,
                    imagesStateRefresh, Modifier, navHostController, starship, starshipViewModel)
        }

        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription("STARSHIPS")

        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()

        val mainTitle = composeTestRule.onNodeWithText("title")
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val model = composeTestRule.onNodeWithText("Model", useUnmergedTree = true)
        model.assertIsDisplayed()
        model.onSibling().assertTextEquals(starship.model)

        val starshipClass = composeTestRule.onNodeWithText("Starship class", useUnmergedTree = true)
        starshipClass.assertIsDisplayed()
        starshipClass.onSibling().assertTextEquals(starship.starshipClass)

        val manufacturers = composeTestRule.onNodeWithText("Manufacturers", useUnmergedTree = true)
        manufacturers.assertIsDisplayed()

        val cost = composeTestRule.onNodeWithText("Cost", useUnmergedTree = true)
        cost.assertIsDisplayed()
        cost.onSibling().assertTextEquals("${starship.cost} ${CREDITS.description}")

        val length = composeTestRule.onNodeWithText("Length", useUnmergedTree = true)
        length.assertIsDisplayed()
        length.onSibling().assertTextEquals("${starship.length} ${M.description}")

        val cargo = composeTestRule.onNodeWithText("Cargo", useUnmergedTree = true)
        cargo.assertIsDisplayed()
        cargo.onSibling().assertTextEquals("${starship.cargoCapacity} ${KG.description}")

        val vMax = composeTestRule.onNodeWithText("V Max", useUnmergedTree = true)
        vMax.assertIsDisplayed()
        vMax.onSibling().assertTextEquals("${starship.vMax} ${KMPERHOUR.description}")

        val hyperdrive = composeTestRule.onNodeWithText("Hyperdrive Class", useUnmergedTree = true)
        hyperdrive.assertIsDisplayed()
        hyperdrive.onSibling().assertTextEquals("${starship.hyperdriveRating} ${CLASS.description}")

        val megalight = composeTestRule.onNodeWithText("Megalight", useUnmergedTree = true)
        megalight.assertIsDisplayed()
        megalight.onSibling().assertTextEquals("${starship.megalight} ${PERHOUR.description}")

        val crew = composeTestRule.onNodeWithText("Crew", useUnmergedTree = true)
        crew.assertIsDisplayed()
        crew.onSibling().assertTextEquals("${starship.crew} ${MEN.description}")

        val passengers = composeTestRule.onNodeWithText("Passengers", useUnmergedTree = true)
        passengers.assertIsDisplayed()
        passengers.onSibling().assertTextEquals("${starship.passengers} ${MEN.description}")

        val consumables = composeTestRule.onNodeWithText("Consumables supply", useUnmergedTree = true)
        consumables.assertIsDisplayed()
        consumables.onSibling().assertTextEquals(starship.consumables)

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val characterBox = composeTestRule.onNodeWithContentDescription("RelatedBox charName", useUnmergedTree = true)
        characterBox.assertIsDisplayed()
        characterBox.assertIsEnabled()
        characterBox.onSibling().assertTextEquals(starship.characterConnection.objects[0].name)

        val movieBox = composeTestRule.onNodeWithContentDescription("RelatedBox movieName", useUnmergedTree = true)
        movieBox.assertIsDisplayed()
        movieBox.assertIsEnabled()
        movieBox.onSibling().assertTextEquals(starship.movieConnection.objects[0].name)

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

        val onClickRight = composeTestRule.onNodeWithContentDescription("onCLickRight", useUnmergedTree = true)
        onClickRight.assertIsDisplayed()
        onClickRight.assertIsEnabled()
        onClickRight.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickRight.onParent().assertIsNotFocused()
    }
}