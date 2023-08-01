package com.project.mypokedex.network.responses

import com.squareup.moshi.Json

data class PokemonSpeciesResponse(
    @field:Json(name = "evolution_chain") val evolutionChain: PokemonSpeciesEvolutionChainResponse,
    val varieties: List<PokemonSpeciesVarietiesResponse>,
    val generation: BasicResponse
)

data class PokemonSpeciesEvolutionChainResponse(
    val url: String
)

data class PokemonSpeciesVarietiesResponse(
    @field:Json(name = "is_default") val isDefault: Boolean,
    val pokemon: BasicResponse
)