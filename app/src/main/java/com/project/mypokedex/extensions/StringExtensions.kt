package com.project.mypokedex.extensions

import com.project.mypokedex.model.SpriteBackground
import com.project.mypokedex.model.SpriteGender
import com.project.mypokedex.model.SpriteOrientation
import com.project.mypokedex.model.SpriteType
import com.project.mypokedex.model.SpriteTypes
import com.project.mypokedex.model.SpriteVariant

fun String.getIDFromURL(): Int {
    return this.split("/").dropLast(1).last().toIntOrNull() ?: -1
}

fun String.getSpriteType(): SpriteType {
    var orientation: SpriteOrientation = SpriteTypes.Front
    var gender: SpriteGender = SpriteTypes.Male
    var variant: SpriteVariant = SpriteTypes.Normal
    var background: SpriteBackground = SpriteTypes.Transparent

    this.split("/").forEach { str ->
        when (str) {
            "back" -> orientation = SpriteTypes.Back
            "female" -> gender = SpriteTypes.Female
            "shiny" -> variant = SpriteTypes.Shiny
            "transparent" -> background = SpriteTypes.Transparent
            "gray" -> background = SpriteTypes.Gray
        }
    }

    return SpriteType(
        orientation,
        gender,
        variant
    )
}