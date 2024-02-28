package com.anjo.starwarswikicompose.presentation.screens.specie.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.DoubleInfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.screens.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SpecieContentScreen(
        navController: NavHostController,
        specieViewModel: SpecieViewModel = hiltViewModel(),
) {
    val specieState by specieViewModel.selectedSpecie.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        specieViewModel.getSpecie()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshImages = { specieViewModel.refreshImages(specieState.specie.id) },
            selectedName = specieState.specie.name,
            saveInDatabase = { specieViewModel.saveInDatabase(specieState.specie.id, it) },
            navController = navController,
            stateObject = specieState.state,
            content = { padding, state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                SpecieScreenContent(padding, state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, specieState.specie, specieViewModel)
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpecieScreenContent(
        padding: PaddingValues,
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        selected: Specie,
        specieViewModel: SpecieViewModel,
) {

    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by specieViewModel.images.collectAsState()
    Box(modifier = modifier.fillMaxSize().padding(padding)
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImage(selected.id, SPECIES),
                    error = choosePainter(SPECIES),
                    contentDescription = stringResource(R.string.species),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(PICTURE_HEIGHT)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(Color.Magenta))
            Text(text = selected.name,
                    fontFamily = SOLOFontName,
                    modifier = Modifier.fillMaxWidth()
                            .height(NAME_PLACEHOLDER_HEIGHT)
                            .basicMarquee(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    color = Color.White
            )
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBox(
                        stringResource(R.string.language_box_name),
                        selected.language,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.homeworld_box_name),
                        selected.homeworld.name,
                        id = selected.homeworld.id,
                        category = PLANETS,
                        width = halfWidth,
                        navController)
            }
            DoubleInfoBox(stringResource(R.string.classification_box_name),
                    selected.classification,
                    stringResource(R.string.designation_box_name),
                    selected.designation,
                    halfWidth)
            DoubleInfoBox(stringResource(R.string.avr_height_box_name),
                    selected.averageHeight,
                    stringResource(R.string.avr_lifespan_box_name),
                    selected.averageLifespan,
                    halfWidth)
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.eye_colors_box_name),
                        null, selected.eyeColors,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.hair_colors_box_name),
                        null, selected.hairColors,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.skin_colors_box_name),
                        null, selected.skinColors,
                        width = thirdWidth)
            }
            ShowHorizontalBoxes(selected.characterConnection, PEOPLE, halfWidth, navController)
            ShowHorizontalBoxes(selected.movieConnection, FILMS, halfWidth, navController)
            GallerySlider(images = imagesState,
                    onCLickLeft = {
                        refreshScope.launch {
                            snackBarHostState.showSnackbar(Constants.REFRESH_IMAGES)
                        }
                        imagesStateRefresh.value = true
                    },
                    onCLickRight = {
                        refreshScope.launch {
                            snackBarHostState.showSnackbar(Constants.DELETE_AND_REFRESH_IMAGES)
                        }
                        specieViewModel.deleteFromDatabase(it)
                        imagesStateRefresh.value = true
                    })
        }
    }
}