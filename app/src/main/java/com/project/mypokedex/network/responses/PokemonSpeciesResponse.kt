package com.project.mypokedex.network.responses

import com.squareup.moshi.Json

data class PokemonSpeciesResponse(
    @field:Json(name = "evolution_chain") val evolutionChain: PokemonSpeciesEvolutionChainResponse
)

data class PokemonSpeciesEvolutionChainResponse(
    val url: String
)