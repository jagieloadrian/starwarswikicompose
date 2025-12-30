package com.anjo.starwarswikicompose.presentation.common.menucomponents

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalHeight
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.Constants.FEEDBACK_RECEIVER
import com.anjo.starwarswikicompose.utils.TestTags.FEEDBACK_CARD_TAG
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine


@Composable
fun FeedbackCard(
        onDismissAction: () -> Unit,
) {
    var subject by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    val appName = stringResource(R.string.app_name)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val height = ((getLocalHeight() / 3) * 2).dp

    Dialog(onDismissRequest = onDismissAction) {
        Card(modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(16.dp)
            .testTag(FEEDBACK_CARD_TAG),
                shape = RoundedCornerShape(16.dp)) {
            Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))) {
                Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .align(Alignment.Center)
                        .background(color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp))
                        .alpha(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly) {
                    Row(modifier = Modifier
                            .fillMaxSize()
                            .weight(1.5f)) {
                        OutlinedTextField(value = subject,
                                onValueChange = {
                                    if (it.isNotEmpty()) {
                                        subject = it
                                    }
                                },
                                singleLine = true,
                                label = {
                                    Text(stringResource(R.string.subject),
                                            color = MaterialTheme.colorScheme.secondary)
                                },
                                placeholder = {
                                    Text(stringResource(R.string.subject_placeholder),
                                            color = MaterialTheme.colorScheme.onSecondary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                                        cursorColor = MaterialTheme.colorScheme.onSecondary,
                                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                ))
                    }
                    Row(modifier = Modifier
                            .fillMaxSize()
                            .weight(5f)) {
                        OutlinedTextField(value = text,
                                onValueChange = {
                                    if (it.isNotEmpty()) {
                                        text = it
                                    }
                                },
                                label = {
                                    Text(stringResource(R.string.description),
                                            color = MaterialTheme.colorScheme.secondary)
                                },
                                placeholder = {
                                    Text(stringResource(R.string.description_placeholder),
                                            color = MaterialTheme.colorScheme.onSecondary)
                                },
                                modifier = Modifier.fillMaxSize(),
                                colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                                        cursorColor = MaterialTheme.colorScheme.onSecondary,
                                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                ))
                    }
                    Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f))
                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround) {
                        TextButton(
                                onClick = {
                                    onDismissAction()
                                },
                                modifier = Modifier
                                        .alpha(0.9f),
                                shape = RoundedCornerShape(SMALL_PADDING),
                                colors = ButtonDefaults.buttonColors(
                                        Color.White.copy(Constants.LESS_WHITE_BACKGROUND_COPY)),
                        ) {
                            Text(text = stringResource(R.string.close_text),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White)
                        }

                        TextButton(
                                onClick = {
                                    scope.launch {
                                        sendFeedback(context, subject, text, appName)
                                    }
                                    onDismissAction()
                                },
                                modifier = Modifier
                                        .alpha(0.9f),
                                shape = RoundedCornerShape(SMALL_PADDING),
                                colors = ButtonDefaults.buttonColors(
                                        Color.White.copy(Constants.LESS_WHITE_BACKGROUND_COPY)),
                        ) {
                            Text(text = stringResource(R.string.send),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

suspend fun sendFeedback(
        activity: Context,
        subject: String,
        body: String,
        appName: String
) = suspendCancellableCoroutine<Unit> {
    val message = "From application $appName\n$body"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(FEEDBACK_RECEIVER))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    activity.startActivity(intent)
}