package com.project.mypokedex.model

data class SpriteType(
    val orientation: SpriteOrientation,
    val gender: SpriteGender,
    val variant: SpriteVariant
) {
    override fun toString(): String {
        return "$orientation|$gender|$variant"
    }

    companion object {
        val defaultType = SpriteType(SpriteTypes.Front, SpriteTypes.Male, SpriteTypes.Normal)

        fun SpriteType.switchGender(): SpriteType {
            val newGender = if (gender == SpriteTypes.Male) SpriteTypes.Female else SpriteTypes.Male
            return SpriteType(orientation, newGender, variant)
        }

        fun SpriteType.switchVariant(): SpriteType {
            val newVariant =
                if (variant == SpriteTypes.Normal) SpriteTypes.Shiny else SpriteTypes.Normal
            return SpriteType(orientation, gender, newVariant)
        }
    }
}
