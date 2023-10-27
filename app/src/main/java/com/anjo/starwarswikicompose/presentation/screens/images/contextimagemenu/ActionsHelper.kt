package com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.imageslider.ActionType
import com.anjo.starwarswikicompose.domain.model.imageslider.ClipBoardAction
import com.anjo.starwarswikicompose.domain.model.imageslider.toImageSliderModel
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.utils.Constants.CLOPY_CLIPBOARD
import com.anjo.starwarswikicompose.utils.Constants.SAVE_IN_PREFIX
import com.anjo.starwarswikicompose.utils.getCategoryByMainUrl

@Composable
@SuppressLint("StateFlowValueCalledInComposition", "RestrictedApi")
fun generateActions(
        navHostController: NavHostController,
        photoUrl: String,
        contextImageModelView: ContextImageModelView,
): List<ClipBoardAction> {
    val lastThreeObjects =
        navHostController.currentBackStack.value.filter { isNotHomeAndImageScreen(it.destination.route) }
                .map { stackEntry -> Pair(first = stackEntry.arguments, second = stackEntry.destination) }
                .map { pair -> buildClipBoardAction(pair, photoUrl, contextImageModelView) }
                .distinct()
                .takeLast(3)
                .toMutableList()

    lastThreeObjects.add(0, buildClipBoardAction(photoUrl))

    return lastThreeObjects
}

fun buildClipBoardAction(
        pair: Pair<Bundle?, NavDestination>, photoUrl: String,
        contextImageModelView: ContextImageModelView,
): ClipBoardAction {
    val category = getCategoryByMainUrl(extractMainUrl(pair.second.route))!!
    val objectId = pair.first!!.getString(category.navArgId)!!
    contextImageModelView.findName(objectId, category)
    val name = contextImageModelView.name.value
    val actionName = "$SAVE_IN_PREFIX $name"

    return ClipBoardAction(
            actionName = actionName,
            objectId = objectId,
            category = category,
            photoUrl = photoUrl,
            action = ActionType.SAVE_TO_OBJECT
    )
}

fun buildClipBoardAction(photoUrl: String): ClipBoardAction {
    return ClipBoardAction(
            actionName = CLOPY_CLIPBOARD,
            photoUrl = photoUrl,
            action = ActionType.SAVE_TO_CLIPBOARD
    )
}

fun isNotHomeAndImageScreen(idRoute: String?): Boolean {
    return idRoute?.let { Screen.Home.route != idRoute && Screen.ImageSearch.route != idRoute } ?: false
}

fun extractMainUrl(navUrl: String?): String {
    return navUrl?.replaceAfter("/", "")?.dropLast(1)!!
}

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("photoUrl", text)
    clipboardManager.setPrimaryClip(clip)
    Toast.makeText(context, "Saved in Clipboard", Toast.LENGTH_SHORT).show()
}

fun saveInRoom(objectItem: ClipBoardAction, contextImageModelView: ContextImageModelView, context: Context) {
    val modelObject = objectItem.toImageSliderModel()
    contextImageModelView.saveInDatabase(modelObject)
    Toast.makeText(context, objectItem.actionName, Toast.LENGTH_SHORT).show()
}