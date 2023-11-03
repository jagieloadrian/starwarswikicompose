package com.anjo.starwarswikicompose.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.anjo.starwarswikicompose.R

enum class MenuItemData(val text: String) {
    Notes("Notes"),
    Feedback("Feedback"),
    Sound("Sound On"),
    Notification("Notification"),
    Info("Info");

    val icon: Painter
        @Composable
        get() = when (this) {
            Notes -> painterResource(R.drawable.baseline_notes_24)
            Feedback  -> painterResource(R.drawable.outline_mail_24)
            Info  -> painterResource(R.drawable.outline_info_24)
            Sound -> painterResource(R.drawable.outline_volume_up_24)
            Notification -> painterResource(R.drawable.baseline_notifications_none_24)
        }
}