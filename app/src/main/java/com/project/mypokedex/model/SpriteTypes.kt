package com.project.mypokedex.model

sealed class SpriteTypes {
    object Front : SpriteOrientation, SpriteTypes()
    object Back : SpriteOrientation, SpriteTypes()
    object Male : SpriteGender, SpriteTypes()
    object Female : SpriteGender, SpriteTypes()
    object Normal : SpriteVariant, SpriteTypes()
    object Shiny : SpriteVariant, SpriteTypes()
    object Transparent : SpriteBackground, SpriteTypes()
    object Gray : SpriteBackground, SpriteTypes()

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

interface SpriteOrientation

interface SpriteGender

interface SpriteVariant

interface SpriteBackground