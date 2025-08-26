package com.anjo.starwarswikicompose.presentation.common.create.models

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.mapper.toChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.presentation.common.button.SaveButton
import com.anjo.starwarswikicompose.presentation.common.modify.addChunksField
import com.anjo.starwarswikicompose.presentation.common.modify.addImageObject
import com.anjo.starwarswikicompose.presentation.common.modify.addListField
import com.anjo.starwarswikicompose.presentation.common.modify.addStringField
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.presentation.screens.create.RunLaunchEffect
import com.anjo.starwarswikicompose.presentation.screens.create.downloadRequiredChunks
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_MOVIE
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.PLANETS_NAME
import com.anjo.starwarswikicompose.utils.Constants.RELEASE_DATE_PATTERN
import com.anjo.starwarswikicompose.utils.Constants.SPECIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_MOVIE_TAG
import com.anjo.starwarswikicompose.utils.getSourceType

@Composable
fun AddMovieObject(modifier: Modifier = Modifier,
                   movieDto: MovieDto,
                   viewModel: AddObjectViewModel,
                   resetObject: () -> Unit = {},
                   onSaveClicked: (String) -> Unit) {
    val state = rememberScrollState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var movieDto by remember { mutableStateOf(movieDto) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val charState by viewModel.charChunks.collectAsState()
    val planetState by viewModel.planetChunks.collectAsState()
    val starshipState by viewModel.starshipChunks.collectAsState()
    val vehicleState by viewModel.vehicleChunks.collectAsState()
    val speciesState by viewModel.specieChunks.collectAsState()
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        downloadRequiredChunks(viewModel, listOf(PEOPLE, PLANETS, STARSHIPS, VEHICLES, SPECIES))
    }

    Box(modifier = modifier
            .fillMaxSize()
            .testTag(ADD_OBJECT_MOVIE_TAG),
            contentAlignment = Alignment.TopCenter) {
        Column(modifier = Modifier
                .padding(SMALL_PADDING)
                .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            movieDto = movieDto.copy(
                    title = addStringField(focusManager = focusManager, placeholder = "Provide title of movie",
                            label = "Title", firstValue = movieDto.title) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(
                    episodeId = addStringField(focusManager = focusManager,
                            placeholder = "Which is part of main saga?",
                            label = "Episode no", firstValue = movieDto.episodeId) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(
                    openingCrawl = addStringField(focusManager = focusManager, singleLine = false, maxLines = 3,
                            placeholder = "Would you like to add an opening crawl?",
                            label = "Opening crawl", firstValue = movieDto.openingCrawl) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(producers = addListField(focusManager = focusManager,
                    placeholder = "Add producers of this work?", label = "Producers", firstValue = movieDto.producers))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(director = addStringField(focusManager = focusManager,
                    placeholder = "Who was director of this work?",
                    label = "Director", firstValue = movieDto.director) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(releaseDate =
                addStringField(focusManager = focusManager, placeholder = "When was release? YYYY-MM-DD",
                        label = "Release Date",
                        firstValue = movieDto.releaseDate) { it.isNotEmpty() && RELEASE_DATE_PATTERN.matches(it) })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(characterConnection =
                addChunksField(focusManager = focusManager, placeholder = "Who was hero?",
                        label = HEROES_NAME, potentialChunks = charState.chunks,
                        firstValue = movieDto.characterConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(planetConnection =
                addChunksField(focusManager = focusManager, placeholder = "Where were action?",
                        label = PLANETS_NAME, potentialChunks = planetState.chunks,
                        firstValue = movieDto.planetConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(starshipConnection =
                addChunksField(focusManager = focusManager, placeholder = "What were spaceships?",
                        label = STARSHIPS_NAME, potentialChunks = starshipState.chunks,
                        firstValue = movieDto.starshipConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(vehicleConnection =
                addChunksField(focusManager = focusManager, placeholder = "What were vehicles?",
                        label = VEHICLES_NAME, potentialChunks = vehicleState.chunks,
                        firstValue = movieDto.vehicleConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            movieDto = movieDto.copy(specieConnection =
                addChunksField(focusManager = focusManager, placeholder = "Which species were fighting?",
                        label = SPECIES_NAME, potentialChunks = speciesState.chunks,
                        firstValue = movieDto.specieConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            imageBitmap =
                addImageObject(context, movieDto.toChunkDto(movieDto.id,
                        getSourceType(movieDto.isFromLocalStore)))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))

            SaveButton(ADD_NEW_MOVIE, isSaving, { verifyDto(movieDto) }) {
                isSaving = true
                viewModel.insertMovie(movieDto.copy(id = ""))
            }
        }
    }

    RunLaunchEffect(viewModel.currentObjectId, context, imageBitmap, isSaving, { resetObject() }) {
        onSaveClicked(viewModel.currentObjectId.value)
    }
}

fun verifyDto(movieDto: MovieDto): Boolean {
    return movieDto.title.isNotEmpty() && movieDto.episodeId.isNotEmpty()
}