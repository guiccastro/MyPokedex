package com.project.mypokedex.network.responses

import com.project.mypokedex.extensions.getIDFromURL
import com.project.mypokedex.model.PokemonColor
import com.project.mypokedex.model.PokemonGeneration

data class BasicResponse(
    val name: String,
    val url: String
) {
    fun createPokemonGeneration(): PokemonGeneration {
        return PokemonGeneration.fromId(url.getIDFromURL())
    }

    fun createPokemonColor(): PokemonColor {
        return PokemonColor.fromId(url.getIDFromURL())
    }
}
