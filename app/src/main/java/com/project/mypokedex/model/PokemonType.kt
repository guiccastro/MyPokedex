package com.project.mypokedex.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.BugColor
import com.project.mypokedex.ui.theme.DarkColor
import com.project.mypokedex.ui.theme.DragonColor
import com.project.mypokedex.ui.theme.ElectricColor
import com.project.mypokedex.ui.theme.FairyColor
import com.project.mypokedex.ui.theme.FightingColor
import com.project.mypokedex.ui.theme.FireColor
import com.project.mypokedex.ui.theme.FlyingColor
import com.project.mypokedex.ui.theme.GhostColor
import com.project.mypokedex.ui.theme.GrassColor
import com.project.mypokedex.ui.theme.GroundColor
import com.project.mypokedex.ui.theme.IceColor
import com.project.mypokedex.ui.theme.NormalColor
import com.project.mypokedex.ui.theme.PoisonColor
import com.project.mypokedex.ui.theme.PsychicColor
import com.project.mypokedex.ui.theme.RockColor
import com.project.mypokedex.ui.theme.ShadowColor
import com.project.mypokedex.ui.theme.SteelColor
import com.project.mypokedex.ui.theme.UnknownColor
import com.project.mypokedex.ui.theme.WaterColor

enum class PokemonType(val id: Int) {
    Normal(1),
    Fighting(2),
    Flying(3),
    Poison(4),
    Ground(5),
    Rock(6),
    Bug(7),
    Ghost(8),
    Steel(9),
    Fire(10),
    Water(11),
    Grass(12),
    Electric(13),
    Psychic(14),
    Ice(15),
    Dragon(16),
    Dark(17),
    Fairy(18),
    Unknown(19),
    Shadow(20);

    fun getColor(): Color {
        return when (this) {
            Normal -> NormalColor
            Fighting -> FightingColor
            Flying -> FlyingColor
            Poison -> PoisonColor
            Ground -> GroundColor
            Rock -> RockColor
            Bug -> BugColor
            Ghost -> GhostColor
            Steel -> SteelColor
            Fire -> FireColor
            Water -> WaterColor
            Grass -> GrassColor
            Electric -> ElectricColor
            Psychic -> PsychicColor
            Ice -> IceColor
            Dragon -> DragonColor
            Dark -> DarkColor
            Fairy -> FairyColor
            Unknown -> UnknownColor
            Shadow -> ShadowColor
        }
    }

    @DrawableRes
    fun getIcon(): Int {
        return when (this) {
            Normal -> R.drawable.ic_normal
            Fighting -> R.drawable.ic_fighting
            Flying -> R.drawable.ic_flying
            Poison -> R.drawable.ic_poison
            Ground -> R.drawable.ic_ground
            Rock -> R.drawable.ic_rock
            Bug -> R.drawable.ic_bug
            Ghost -> R.drawable.ic_ghost
            Steel -> R.drawable.ic_steel
            Fire -> R.drawable.ic_fire
            Water -> R.drawable.ic_water
            Grass -> R.drawable.ic_grass
            Electric -> R.drawable.ic_electric
            Psychic -> R.drawable.ic_psychic
            Ice -> R.drawable.ic_ice
            Dragon -> R.drawable.ic_dragon
            Dark -> R.drawable.ic_dark
            Fairy -> R.drawable.ic_fairy
            Unknown -> R.drawable.ic_close
            Shadow -> R.drawable.ic_close
        }
    }

    companion object {
        fun fromId(id: Int): PokemonType? {
            return values().firstOrNull { it.id == id }
        }
    }

    override fun toString(): String {
        return name
    }
}