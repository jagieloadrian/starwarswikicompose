package com.anjo.starwarswikicompose.presentation.screens.person.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
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
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.AddImageFabWrap
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PersonContentScreen(
        navController: NavHostController,
        personViewModel: PersonViewModel = hiltViewModel(),
) {
    val selectedPerson by personViewModel.selectedPerson.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        personViewModel.getPerson()
        init.value = false
    }
    PersonVisualisation(selectedPerson, navController, personViewModel)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun PersonVisualisation(
        selected: Person,
        navController: NavHostController,
        personViewModel: PersonViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val imagesState by personViewModel.images.collectAsState()
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val clipManager = LocalClipboardManager.current
    val snackBarHostState = remember { SnackbarHostState() }
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            personViewModel.refreshImages(selected.id)
            delay(1500)
            isRefreshing = false
        }
    })

    LaunchedEffect(imagesStateRefresh.value) {
        personViewModel.refreshImages(selected.id)
        delay(1000)
        imagesStateRefresh.value = false
    }

    LaunchedEffect(state) {
        var prev = 0
        snapshotFlow { state.value }.collect {
            fabExtended = it <= prev
            prev = it
        }
    }

    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) },
            floatingActionButton = {
                AddImageFabWrap(fabExtended, clipManager, navController, selected.name, {
                    personViewModel.saveInDatabase(selected.id, it)
                    imagesStateRefresh.value = true
                }, refreshScope, snackBarHostState)
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                if (!isRefreshing) {
                    AsyncImage(model = findImage(selected.id, PEOPLE),
                            error = choosePainter(PEOPLE),
                            contentDescription = stringResource(R.string.people),
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
                            horizontalArrangement = Arrangement.SpaceAround) {
                        InfoBox(
                                stringResource(R.string.homeworld_box_name),
                                selected.homeworld.name,
                                id = selected.homeworld.id,
                                category = PLANETS,
                                width = halfWidth,
                                navController)
                        InfoBox(
                                stringResource(R.string.species_box_name),
                                selected.specie.name,
                                id = selected.specie.id,
                                category = SPECIES,
                                width = halfWidth,
                                navController)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBox(
                                stringResource(R.string.birth_box_name),
                                selected.birthYear,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.height_box_name),
                                selected.height,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.mass_box_name),
                                selected.mass,
                                width = thirdWidth)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBox(
                                stringResource(R.string.gender_box_name),
                                selected.gender,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.hair_box_name),
                                selected.hair,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.skin_box_name),
                                selected.skin,
                                width = thirdWidth)
                    }
                }
                ShowHorizontalBoxes(selected.movieConnection, FILMS, halfWidth, navController)
                ShowHorizontalBoxes(selected.starshipConnection, STARSHIPS, halfWidth, navController)
                ShowHorizontalBoxes(selected.vehicleConnection, VEHICLES, halfWidth, navController)
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
                            personViewModel.deleteFromDatabase(it)
                            imagesStateRefresh.value = true

                        })
            }
            PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}