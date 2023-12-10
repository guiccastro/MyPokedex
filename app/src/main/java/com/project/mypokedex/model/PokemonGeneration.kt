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
}