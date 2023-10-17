package com.project.mypokedex.model

import androidx.compose.ui.graphics.Color
import com.project.mypokedex.ui.theme.DarkColor
import com.project.mypokedex.ui.theme.ElectricColor
import com.project.mypokedex.ui.theme.FairyColor
import com.project.mypokedex.ui.theme.FightingColor
import com.project.mypokedex.ui.theme.GhostColor
import com.project.mypokedex.ui.theme.GrassColor
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.NormalColor
import com.project.mypokedex.ui.theme.RockColor
import com.project.mypokedex.ui.theme.WaterColor

enum class PokemonColor(val id: Int) {
    Black(1),
    Blue(2),
    Brown(3),
    Gray(4),
    Green(5),
    Pink(6),
    Purple(7),
    Red(8),
    White(9),
    Yellow(10);

    companion object {
        fun fromId(id: Int): PokemonColor {
            return values().firstOrNull { it.id == id } ?: Black
        }
    }

    fun getColor(): Color {
        return when (this) {
            Black -> DarkColor
            Blue -> WaterColor
            Brown -> RockColor
            Gray -> NormalColor
            Green -> GrassColor
            Pink -> FairyColor
            Purple -> GhostColor
            Red -> FightingColor
            White -> MainWhite
            Yellow -> ElectricColor
        }
    }
}