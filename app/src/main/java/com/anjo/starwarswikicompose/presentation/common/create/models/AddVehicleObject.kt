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
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
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
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_VEHICLE
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_VEHICLE_TAG
import com.anjo.starwarswikicompose.utils.getSourceType

@Composable
fun AddVehicleObject(modifier: Modifier = Modifier,
                     vehicleDto: VehicleDto,
                     viewModel: AddObjectViewModel,
                     resetObject: () -> Unit = {},
                     onSaveClicked: (String) -> Unit) {

    val state = rememberScrollState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var vehicleDto by remember { mutableStateOf(vehicleDto) }

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
            .testTag(ADD_OBJECT_VEHICLE_TAG),
            contentAlignment = Alignment.TopCenter) {
        Column(modifier = modifier
                .padding(SMALL_PADDING)
                .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            vehicleDto = vehicleDto.copy(name = addStringField(focusManager = focusManager,
                    placeholder = "Provide name of vehicle", label = "Vehicle",
                    firstValue = vehicleDto.name) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(model = addStringField(focusManager = focusManager,
                    placeholder = "Provide model of vehicle", label = "Model",
                    firstValue = vehicleDto.model) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(vehicleClass = addStringField(focusManager = focusManager,
                    placeholder = "Provide class of vehicle", label = "Class",
                    firstValue = vehicleDto.vehicleClass) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(manufacturers = addListField(focusManager = focusManager,
                    placeholder = "What create this vehicle?", label = "Manufacturers",
                    firstValue = vehicleDto.manufacturers))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(cost = addStringField(focusManager = focusManager,
                    placeholder = "How expensive is this vehicle?", label = "Cost",
                    firstValue = vehicleDto.cost) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(length = addStringField(focusManager = focusManager,
                    placeholder = "Who long is  this vehicle?", label = "Length",
                    firstValue = vehicleDto.length) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(crew = addStringField(focusManager = focusManager,
                    placeholder = "How many crew is needed to handle this vehicle?",
                    label = "Crew", firstValue = vehicleDto.crew) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(passengers = addStringField(focusManager = focusManager,
                    placeholder = "How many passengers can this vehicle carry?",
                    label = "Passengers", firstValue = vehicleDto.passengers) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(vMax = addStringField(focusManager = focusManager,
                    placeholder = "How fast is this vehicle?", label = "V Max",
                    firstValue = vehicleDto.vMax) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(cargoCapacity = addStringField(focusManager = focusManager,
                    placeholder = "How much can this vehicle carry?", label = "Cargo capacity",
                    firstValue = vehicleDto.cargoCapacity) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(consumables = addStringField(focusManager = focusManager,
                    placeholder = "How long passengers can live on this vehicle?",
                    label = "Consumables", firstValue = vehicleDto.consumables) { it.isNotEmpty() })
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(characterConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Who was driving this vehicle?", label = HEROES_NAME,
                    potentialChunks = charState.chunks, firstValue = vehicleDto.characterConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            vehicleDto = vehicleDto.copy(movieConnection = addChunksField(focusManager = focusManager,
                    placeholder = "Which movies appear your vehicle?", label = MOVIES_NAME,
                    potentialChunks = movieState.chunks, firstValue = vehicleDto.movieConnection.objects))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))
            imageBitmap = addImageObject(context, vehicleDto.toChunkDto(vehicleDto.id,
                    getSourceType(vehicleDto.isFromLocalStore)))
            Spacer(modifier = Modifier.size(MEDIUM_PADDING))

            SaveButton(ADD_NEW_VEHICLE, isSaving, { verifyDto(vehicleDto) }) {
                isSaving = true
                viewModel.insertVehicle(vehicleDto)
            }
        }
    }

    RunLaunchEffect(viewModel.currentObjectId, context, imageBitmap, isSaving,
            { resetObject() }) {
        onSaveClicked(viewModel.currentObjectId.value)
    }
}

fun verifyDto(vehicleDto: VehicleDto): Boolean {
    return vehicleDto.name.isNotEmpty() && vehicleDto.model.isNotEmpty()
}