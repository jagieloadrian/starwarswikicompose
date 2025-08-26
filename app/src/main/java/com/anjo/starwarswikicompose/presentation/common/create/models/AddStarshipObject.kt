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
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.mapper.toChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
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
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_STARSHIP
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_STARSHIP_TAG
import com.anjo.starwarswikicompose.utils.getSourceType

@Composable
fun AddStarshipObject(modifier: Modifier = Modifier,
                      starshipDto: StarshipDto,
                      viewModel: AddObjectViewModel,
                      resetObject: () -> Unit = {},
                      onSaveClicked: (String) -> Unit) {

    val state = rememberScrollState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var starshipDto by remember { mutableStateOf(starshipDto) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val movieState by viewModel.movieChunks.collectAsState()
    val charState by viewModel.charChunks.collectAsState()
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        downloadRequiredChunks(viewModel, listOf(PEOPLE, FILMS))
    }

    Box(modifier = modifier
            .fillMaxSize()
            .testTag(ADD_OBJECT_STARSHIP_TAG),
            contentAlignment = Alignment.TopCenter) {
        Column(modifier = modifier
                .padding(SMALL_PADDING)
                .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            starshipDto = starshipDto.copy(name = addStringField(focusManager = focusManager,
                    placeholder = "Provide name of starship", label = "Starship",
                    firstValue = starshipDto.name) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(model = addStringField(focusManager = focusManager,
                    placeholder = "Provide model of starship", label = "Model",
                    firstValue = starshipDto.model) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(starshipClass = addStringField(focusManager = focusManager,
                    placeholder = "Provide class of starship", label = "Class",
                    firstValue = starshipDto.starshipClass) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(manufacturers = addListField(focusManager = focusManager,
                    placeholder = "What create this starship?", label = "Manufacturers",
                    firstValue = starshipDto.manufacturers))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(cost = addStringField(focusManager = focusManager,
                    placeholder = "How expensive is this starship?", label = "Cost",
                    firstValue = starshipDto.cost) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(length = addStringField(focusManager = focusManager,
                    placeholder = "Who long is  this starship?", label = "Length",
                    firstValue = starshipDto.length) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(cargoCapacity = addStringField(focusManager = focusManager,
                    placeholder = "How much can this starship carry?", label = "Cargo capacity",
                    firstValue = starshipDto.cargoCapacity) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(vMax = addStringField(focusManager = focusManager,
                    placeholder = "How fast is this starship?", label = "V Max",
                    firstValue = starshipDto.vMax) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(hyperdriveRating = addStringField(focusManager = focusManager,
                    placeholder = "Which class of hyperdriving has this starship?",
                    label = "Hyperdrive Rating", firstValue = starshipDto.hyperdriveRating) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(megalight = addStringField(focusManager = focusManager,
                    placeholder = "How fast is this starship in megalights?", label = "Megalight",
                    firstValue = starshipDto.megalight) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(crew = addStringField(focusManager = focusManager,
                    placeholder = "How many crew is needed to handle this starship?",
                    label = "Crew", firstValue = starshipDto.crew) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(passengers = addStringField(focusManager = focusManager,
                    placeholder = "How many passengers can this starship carry?",
                    label = "Passengers", firstValue = starshipDto.passengers) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(consumables = addStringField(focusManager = focusManager,
                    placeholder = "How long passengers can live on this starship?",
                    label = "Consumables", firstValue = starshipDto.consumables) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(characterConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Who was flying this spaceship?",
                    label = HEROES_NAME, potentialChunks = charState.chunks,
                    firstValue = starshipDto.characterConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            starshipDto = starshipDto.copy(movieConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which movies appear your starship?",
                    label = MOVIES_NAME, potentialChunks = movieState.chunks,
                    firstValue = starshipDto.movieConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            imageBitmap = addImageObject(context, starshipDto.toChunkDto(starshipDto.id,
                    getSourceType(starshipDto.isFromLocalStore)))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))

            SaveButton(ADD_NEW_STARSHIP, isSaving, { verifyDto(starshipDto) }) {
                isSaving = true
                viewModel.insertStarship(starshipDto)
            }
        }
    }

    RunLaunchEffect(viewModel.currentObjectId, context, imageBitmap, isSaving,
            { resetObject() }) {
        onSaveClicked(viewModel.currentObjectId.value)
    }
}

fun verifyDto(starshipDto: StarshipDto): Boolean {
    return starshipDto.name.isNotEmpty() && starshipDto.model.isNotEmpty()
}
