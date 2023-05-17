package com.project.mypokedex.data

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    private fun getColor(): Color {
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

    companion object {
        fun fromName(name: String): PokemonType? {
            return values().firstOrNull { it.name.lowercase() == name.lowercase() }
        }

        fun fromId(id: Int): PokemonType? {
            return values().firstOrNull { it.id == id }
        }
    }

    override fun toString(): String {
        return name
    }

    @Composable
    fun ToUI(modifier: Modifier = Modifier, fontSize: TextUnit = 8.sp) {
        val cornerRoundWeight = 3 / 2
        val cornerShape = RoundedCornerShape((fontSize.value * cornerRoundWeight).dp)
        Text(
            modifier = modifier
                .border(width = (fontSize.value / 35).dp, Color.Black, cornerShape)
                .border(width = (fontSize.value / 7).dp, color = getColor(), cornerShape)
                .background(Color.Black.copy(alpha = 0.5f), cornerShape)
                .padding(
                    horizontal = (fontSize.value * 2 / 3).dp,
                    vertical = (fontSize.value / 17).dp
                ),
            text = toString().uppercase(),
            color = getColor(),
            fontSize = fontSize,
            fontWeight = FontWeight(500),
            letterSpacing = (0.2).sp
        )
    }
}

@Preview
@Composable
fun TypePreview() {
    PokemonType.Fire.ToUI()
}