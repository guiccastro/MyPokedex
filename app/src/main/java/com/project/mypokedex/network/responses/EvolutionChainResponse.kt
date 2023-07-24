package com.project.mypokedex.network.responses

import com.squareup.moshi.Json

data class EvolutionChainBase(
    val chain: EvolutionChainResponse
)

data class EvolutionChainResponse(
    @field:Json(name = "evolves_to") val evolvesToList: List<EvolutionChainResponse>,
    val species: BasicResponse
)