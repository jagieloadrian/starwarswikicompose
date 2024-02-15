package com.anjo.starwarswikicompose.presentation.screens.planet.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Planet
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
class PlanetScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navHostController: NavHostController
    private lateinit var planetViewModel: PlanetViewModel

    @Test
    fun givenPlanet_whenDisplayed_thenAsserResults(): Unit = runBlocking {
        //given
        val planet = Planet(id = "objectId1", name = "title", diameter = "1", gravity = "height",
                population = "it was", rotationPeriod = "gender", orbitalPeriod = "hairs",
                climates = listOf("skins", "skulls"), surfaceWater = "water", terrains = listOf("ground", "stones"),
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "movieId", name = "movieName")))
        )

        planetViewModel = Mockito.mock(PlanetViewModel::class.java)

        val imagesResponse = listOf(ImageSliderModel(1, planet.id, "someUrl"))
        lateinit var imagesFLow: StateFlow<List<ImageSliderModel>>
        val scope = CoroutineScope(coroutineContext)
        flow { emit(imagesResponse) }.stateIn(scope).also { imagesFLow = it }

        planetViewModel.stub {
            on { images } doReturn imagesFLow
        }
        composeTestRule.setContent {
            navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            PlanetScreenContent(PaddingValues(0.dp), state, scope, snackBarHostState,
                    imagesStateRefresh, navHostController, planet, planetViewModel)
        }
        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription("PLANETS")

        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()

        val mainTitle = composeTestRule.onNodeWithText("title")
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val diameter = composeTestRule.onNodeWithText("Diameter", useUnmergedTree = true)
        diameter.assertIsDisplayed()
        diameter.onSibling().assertTextEquals(planet.diameter)

        val gravity = composeTestRule.onNodeWithText("Gravity", useUnmergedTree = true)
        gravity.assertIsDisplayed()
        gravity.onSibling().assertTextEquals(planet.gravity)

        val population = composeTestRule.onNodeWithText("Population", useUnmergedTree = true)
        population.assertIsDisplayed()
        population.onSibling().assertTextContains("${planet.population} citizens")

        val rotationPeriod = composeTestRule.onNodeWithText("Rotation Period", useUnmergedTree = true)
        rotationPeriod.assertIsDisplayed()
        rotationPeriod.onSibling().assertTextEquals(planet.rotationPeriod)

        val orbitalPeriod = composeTestRule.onNodeWithText("Orbital Period", useUnmergedTree = true)
        orbitalPeriod.assertIsDisplayed()
        orbitalPeriod.onSibling().assertTextEquals(planet.orbitalPeriod)

        val climates = composeTestRule.onNodeWithText("Climates", useUnmergedTree = true)
        climates.assertIsDisplayed()

        val surfaceWater = composeTestRule.onNodeWithText("Surface Water", useUnmergedTree = true)
        surfaceWater.assertIsDisplayed()
        surfaceWater.onSibling().assertTextEquals(planet.surfaceWater)

        val terrains = composeTestRule.onNodeWithText("Terrains", useUnmergedTree = true)
        terrains.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val characterBox = composeTestRule.onNodeWithContentDescription("RelatedBox charName", useUnmergedTree = true)
        characterBox.assertIsDisplayed()
        characterBox.assertIsEnabled()
        characterBox.onSibling().assertTextEquals(planet.characterConnection.objects[0].name)

        val movieBox = composeTestRule.onNodeWithContentDescription("RelatedBox movieName", useUnmergedTree = true)
        movieBox.assertIsDisplayed()
        movieBox.assertIsEnabled()
        movieBox.onSibling().assertTextEquals(planet.movieConnection.objects[0].name)


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