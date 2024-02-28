package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.test.assertIsNotEnabled
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Movie
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
class MovieScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var navHostController: NavHostController
    private lateinit var movieViewModel: MovieViewModel

    @Test
    fun givenMovie_whenDisplayed_thenAssertResults(): Unit = runBlocking {
        val movie = Movie(id = "objectId1", title = "title", episodeId = "1", openingCrawl = "longText",
                releaseDate = "it was", director = "director", producers = listOf("producer1", "producer2"),
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                planetConnection = Connection(1, listOf(UniversalChunk(id = "planetId", name = "planetName"))),
                vehicleConnection = Connection(1, listOf(UniversalChunk(id = "vehicleId", name = "vehicleName"))),
                starshipConnection = Connection(1, listOf(UniversalChunk(id = "starshipId", name = "StarshipName"))),
                specieConnection = Connection(1, listOf(UniversalChunk(id = "connectId", name = "specieName"))))

        movieViewModel = mock(MovieViewModel::class.java)

        val imagesResponse = listOf(ImageSliderModel(1, movie.id, "someUrl"))
        lateinit var imagesFLow: StateFlow<List<ImageSliderModel>>
        val scope = CoroutineScope(coroutineContext)
        flow { emit(imagesResponse) }.stateIn(scope).also { imagesFLow = it }


        movieViewModel.stub {
            on { images } doReturn imagesFLow
        }

        composeRule.setContent {
            navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            MovieScreen(PaddingValues(0.dp), state,scope, snackBarHostState,
                    imagesStateRefresh, Modifier, navHostController, movie, movieViewModel)
        }
        //when and then
        val mainImage = composeRule.onNodeWithContentDescription("MOVIES")

        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()

        val mainTitle = composeRule.onNodeWithText("title")
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val episodeBox = composeRule.onNodeWithText("Episode")
        episodeBox.assertIsDisplayed()
        episodeBox.assertIsNotEnabled()

        val openingCrawl = composeRule.onNodeWithText("Opening Crawl", useUnmergedTree = true)
        openingCrawl.assertIsDisplayed()
        openingCrawl.onSibling().assertTextEquals(movie.openingCrawl)

        val producersName = composeRule.onNodeWithText("Producers")
        producersName.assertIsDisplayed()

        val producers = composeRule.onNodeWithText("producer1", true, true)
        producers.assertIsDisplayed()
        producers.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))

        val director = composeRule.onNodeWithText("Director")
        director.assertIsDisplayed()
        director.assertIsNotEnabled()

        val releaseDate = composeRule.onNodeWithText("Release Date", useUnmergedTree = true)
        releaseDate.assertIsDisplayed()
        releaseDate.assertIsEnabled()
        releaseDate.onSibling().assertTextEquals(movie.releaseDate)

        val characterBox = composeRule.onNodeWithContentDescription("RelatedBox charName", useUnmergedTree = true)
        characterBox.assertIsDisplayed()
        characterBox.assertIsEnabled()
        characterBox.onSibling().assertTextEquals(movie.characterConnection.objects[0].name)

        val planetBox = composeRule.onNodeWithContentDescription("RelatedBox planetName", useUnmergedTree = true)
        planetBox.assertIsDisplayed()
        planetBox.assertIsEnabled()
        planetBox.onSibling().assertTextEquals(movie.planetConnection.objects[0].name)

        composeRule.onRoot().performTouchInput { swipeUp() }

        val starshipBox = composeRule.onNodeWithContentDescription("RelatedBox StarshipName", useUnmergedTree = true)
        starshipBox.assertIsDisplayed()
        starshipBox.assertIsEnabled()
        starshipBox.onSibling().assertTextEquals(movie.starshipConnection.objects[0].name)

        val vehicleBox = composeRule.onNodeWithContentDescription("RelatedBox vehicleName", useUnmergedTree = true)
        vehicleBox.assertIsDisplayed()
        vehicleBox.assertIsEnabled()
        vehicleBox.onSibling().assertTextEquals(movie.vehicleConnection.objects[0].name)

        val specieBox = composeRule.onNodeWithContentDescription("RelatedBox specieName", useUnmergedTree = true)
        specieBox.assertIsDisplayed()
        specieBox.assertIsEnabled()
        specieBox.onSibling().assertTextEquals(movie.specieConnection.objects[0].name)

        val image = composeRule.onNodeWithContentDescription("image from flickr",
                substring = true,
                ignoreCase = true,
                useUnmergedTree = true)

        image.assertIsDisplayed()
        image.assertIsEnabled()

        val onClickLeft = composeRule.onNodeWithContentDescription("onClickLeft", useUnmergedTree = true)
        onClickLeft.assertIsDisplayed()
        onClickLeft.assertIsEnabled()
        onClickLeft.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickLeft.onParent().assertIsNotFocused()

        val onClickRight = composeRule.onNodeWithContentDescription("onCLickRight",  useUnmergedTree = true)
        onClickRight.assertIsDisplayed()
        onClickRight.assertIsEnabled()
        onClickRight.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickRight.onParent().assertIsNotFocused()
    }
}