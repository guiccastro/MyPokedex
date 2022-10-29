package com.project.mypokedex.data

import com.project.mypokedex.firstLetterUppercase

class PokemonType(
    val name: String
) {

    override fun toString(): String {
        return name.firstLetterUppercase()
    }
}