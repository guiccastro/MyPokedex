package com.project.mypokedex.extensions

import com.project.mypokedex.model.SpriteType

fun String.getIDFromURL(): Int {
    return this.split("/").dropLast(1).last().toIntOrNull() ?: -1
}

fun String.getSpriteTypes(): List<SpriteType> {
    val typeList = ArrayList<SpriteType>()

    var genderDefined = false
    var directionDefined = false
    var typeDefined = false

    this.split("/").forEach { str ->
        when (str) {
            "back" -> {
                typeList.add(SpriteType.Back)
                directionDefined = true
            }

            "female" -> {
                typeList.add(SpriteType.Female)
                genderDefined = true
            }

            "shiny" -> {
                typeList.add(SpriteType.Shiny)
                typeDefined = true
            }

            "transparent" -> typeList.add(SpriteType.Transparent)
            "gray" -> typeList.add(SpriteType.Gray)
        }
    }

    if (!directionDefined) typeList.add(SpriteType.Front)
    if (!genderDefined) typeList.add(SpriteType.Male)
    if (!typeDefined) typeList.add(SpriteType.Normal)

    return typeList
}