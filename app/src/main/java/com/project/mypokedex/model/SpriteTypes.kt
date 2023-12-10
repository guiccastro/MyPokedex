package com.project.mypokedex.model

import androidx.annotation.StringRes
import com.project.mypokedex.R

sealed class SpriteTypes {
    object Front : SpriteOrientation, SpriteTypes()
    object Back : SpriteOrientation, SpriteTypes()
    object Male : SpriteGender, SpriteTypes()
    object Female : SpriteGender, SpriteTypes()
    object Normal : SpriteVariant, SpriteTypes()
    object Shiny : SpriteVariant, SpriteTypes()
    object Transparent : SpriteBackground, SpriteTypes()
    object Gray : SpriteBackground, SpriteTypes()

    @StringRes
    fun getStringRes(): Int {
        return when (this) {
            Front -> R.string.front
            Back -> R.string.back
            Female -> R.string.female
            Gray -> R.string.gray
            Male -> R.string.male
            Normal -> R.string.normal
            Shiny -> R.string.shiny
            Transparent -> R.string.transparent
        }
    }
}

interface SpriteOrientation

interface SpriteGender

interface SpriteVariant

interface SpriteBackground