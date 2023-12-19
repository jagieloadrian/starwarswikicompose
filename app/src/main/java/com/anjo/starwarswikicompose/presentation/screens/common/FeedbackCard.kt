package com.anjo.starwarswikicompose.presentation.screens.common

import android.util.Log
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.anjo.starwarswikicompose.BuildConfig.FEEDBACK_RECEIVER_PASSWORD
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.reverseMainBackgroundColors
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.Constants.FEEDBACK_RECEIVER
import com.anjo.starwarswikicompose.utils.getLocalHeight
import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties


@Composable
fun FeedbackCard(
        onDismissAction: () -> Unit,
) {
    var userEmail by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    val appName = stringResource(R.string.app_name)

    val height = ((getLocalHeight() / 3) * 2).dp
    Dialog(onDismissRequest = onDismissAction) {
        Card(modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(16.dp),
                shape = RoundedCornerShape(16.dp)) {
            Box(modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colors.mainBackgroundColors)
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))) {
                Column(modifier = Modifier.fillMaxSize()
                        .padding(10.dp)
                        .align(Alignment.Center)
                        .background(color = MaterialTheme.colors.mainBackgroundColors,
                                shape = RoundedCornerShape(16.dp))
                        .alpha(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween) {
                    Row(
                            modifier = Modifier.fillMaxSize()
                                    .weight(1.5f),
                    ) {
                        OutlinedTextField(value = userEmail,
                                singleLine = true,
                                onValueChange = { userEmail = it },
                                label = { Text(stringResource(R.string.user_email)) },
                                placeholder = { Text(stringResource(R.string.user_email_placeholder)) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.White,
                                        focusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        focusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        cursorColor = MaterialTheme.colors.primary
                                ))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth()
                            .weight(0.1f))
                    Row(
                            modifier = Modifier.fillMaxSize()
                                    .weight(1.5f),
                    ) {
                        OutlinedTextField(value = subject,
                                onValueChange = {
                                    if (it.isNotEmpty()) {
                                        subject = it
                                    }
                                },
                                singleLine = true,
                                label = { Text(stringResource(R.string.subject)) },
                                placeholder = { Text(stringResource(R.string.subject_placeholder)) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.White,
                                        focusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        focusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        cursorColor = MaterialTheme.colors.primary
                                ))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth()
                            .weight(0.1f))
                    Row(
                            modifier = Modifier.fillMaxSize()
                                    .weight(5f),
                    ) {
                        OutlinedTextField(value = text,
                                onValueChange = {
                                    if (it.isNotEmpty()) {
                                        text = it
                                    }
                                },
                                label = { Text(stringResource(R.string.description)) },
                                placeholder = { Text(stringResource(R.string.description_placeholder)) },
                                modifier = Modifier.fillMaxSize(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.White,
                                        focusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedLabelColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        focusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        unfocusedBorderColor = (MaterialTheme.colors
                                                .reverseMainBackgroundColors),
                                        cursorColor = MaterialTheme.colors.primary
                                ))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth()
                            .weight(0.2f))
                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                        TextButton(
                                onClick = {
                                    sendFeedback(userEmail, subject, text, appName)
                                    onDismissAction()
                                },
                                modifier = Modifier
                                        .alpha(0.9f),
                                shape = RoundedCornerShape(SMALL_PADDING),
                                colors = ButtonDefaults.buttonColors(
                                        Color.White.copy(Constants.LESS_WHITE_BACKGROUND_COPY)),
                        ) {
                            Text(text = stringResource(R.string.send_close),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

fun sendFeedback(
        email: String?,
        subject: String,
        description: String,
        appName: String,
) {
    CoroutineScope(Dispatchers.IO).launch {
        val host = "smtp.gmail.com"
        val port = 587
        val userName = FEEDBACK_RECEIVER
        val password = FEEDBACK_RECEIVER_PASSWORD

        val to = FEEDBACK_RECEIVER

        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = host
        props["mail.smtp.port"] = port

        val session = Session.getDefaultInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(userName, password)
            }
        })

        try {
            // Create a new MimeMessage
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(userName))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(to))
            message.subject = subject
            if (email.isNullOrEmpty()) {
                message.setText("From application $appName\n$description")
            } else {
                message.setText("From application $appName and user email: $email\n$description")
            }

            Transport.send(message)

        } catch (e: MessagingException) {
            Log.e("EMAIL", e.toString())
        }
    }
}
