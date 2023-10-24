package com.anjo.starwarswikicompose.domain.model.imageslider

import com.anjo.starwarswikicompose.utils.Category

data class ClipBoardAction(
        val actionName: String="",
        val objectId: String = "",
        val category: Category = Category.FILMS,
        val photoUrl: String="",
        val action: ActionType=ActionType.SAVE_TO_OBJECT,
)

enum class ActionType {
    SAVE_TO_OBJECT, SAVE_TO_CLIPBOARD
}

fun ClipBoardAction.toImageSliderModel(): ImageSliderModel {
    return ImageSliderModel(
            objectId = objectId,
            url = photoUrl,
            objectType = category,
    )
}
