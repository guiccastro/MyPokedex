package com.project.mypokedex.model

enum class PokemonGeneration(val id: Int) {
    Gen1(1),
    Gen2(2),
    Gen3(3),
    Gen4(4),
    Gen5(5),
    Gen6(6),
    Gen7(7),
    Gen8(8),
    Gen9(9),
    Unknown(0);

    companion object {
        fun fromId(id: Int): PokemonGeneration {
            return PokemonGeneration.values().firstOrNull { it.id == id } ?: Unknown
        }
    }

    fun getTitle(): String {
        return when (this) {
            Gen1 -> "Gen 1"
            Gen2 -> "Gen 2"
            Gen3 -> "Gen 3"
            Gen4 -> "Gen 4"
            Gen5 -> "Gen 5"
            Gen6 -> "Gen 6"
            Gen7 -> "Gen 7"
            Gen8 -> "Gen 8"
            Gen9 -> "Gen 9"
            else -> "-"
        }
    }
}