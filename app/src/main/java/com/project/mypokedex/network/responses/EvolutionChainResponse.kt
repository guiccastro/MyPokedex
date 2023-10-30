package com.project.mypokedex.network.responses

import com.project.mypokedex.extensions.getIDFromURL
import com.project.mypokedex.model.EvolutionChainItem
import com.squareup.moshi.Json

data class EvolutionChainBase(
    val chain: EvolutionChainResponse
)

data class EvolutionChainResponse(
    @field:Json(name = "evolves_to") val evolvesToList: List<EvolutionChainResponse>,
    val species: BasicResponse
) {
    fun createEvolutionChainItem(): EvolutionChainItem {
        return EvolutionChainItem(
            pokemonId = species.url.getIDFromURL(),
            evolvesTo = evolvesToList.map { it.createEvolutionChainItem() }
        )
    }
}