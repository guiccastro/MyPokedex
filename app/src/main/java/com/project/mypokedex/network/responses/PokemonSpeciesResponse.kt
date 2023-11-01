package com.project.mypokedex.network.responses

import android.util.Log
import com.project.mypokedex.extensions.getIDFromURL
import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.EvolutionChainItem
import com.project.mypokedex.model.PokemonColor
import com.project.mypokedex.model.PokemonGeneration
import com.project.mypokedex.model.PokemonSpecies
import com.project.mypokedex.network.services.EvolutionChainService
import com.squareup.moshi.Json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

data class PokemonSpeciesResponse(
    @field:Json(name = "evolution_chain") val evolutionChainResponse: PokemonSpeciesEvolutionChainResponse,
    @field:Json(name = "varieties") val varietiesResponse: List<PokemonSpeciesVarietiesResponse>,
    @field:Json(name = "generation") val generationResponse: BasicResponse,
    @field:Json(name = "color") val colorResponse: BasicResponse
) {
    suspend fun createPokemonSpecies(evolutionChainClient: EvolutionChainService): PokemonSpecies {
        Log.i("PokemonSpeciesResponse", "createPokemonSpecies: Creating pokemon species")
        var evolutionChain = EvolutionChain(EvolutionChainItem(0, emptyList()))
        var varieties: List<Int> = emptyList()
        var generation: PokemonGeneration = PokemonGeneration.Unknown
        var color: PokemonColor = PokemonColor.Black

        Log.println(Log.ASSERT, "createPokemonSpeciesAssert", this.toString())
        CoroutineScope(Dispatchers.Main).launch {
            async {
                evolutionChain = evolutionChainResponse.createEvolutionChain(evolutionChainClient)
            }
            async {
                varieties = varietiesResponse.createPokemonVarieties()
            }
            async {
                generation = generationResponse.createPokemonGeneration()
            }
            async {
                color = colorResponse.createPokemonColor()
            }
        }.join()
        Log.i("PokemonSpeciesResponse", "createPokemonSpecies: Pokemon species created")

        return PokemonSpecies(evolutionChain, varieties, generation)
    }

    private fun List<PokemonSpeciesVarietiesResponse>.createPokemonVarieties(): List<Int> {
        return this.map { it.pokemon.url.getIDFromURL() }
    }
}

data class PokemonSpeciesEvolutionChainResponse(
    val url: String
) {
    suspend fun createEvolutionChain(evolutionChainClient: EvolutionChainService): EvolutionChain {
        return try {
            Log.i("PokemonSpeciesResponse", "createEvolutionChain: Creating evolution chain")
            val evolutionChainId = url.getIDFromURL()
            val evolutionChainResponse =
                evolutionChainClient.getEvolutionChain(evolutionChainId).chain
            val evolutionChainBaseItem = evolutionChainResponse.createEvolutionChainItem()
            Log.i("PokemonSpeciesResponse", "createEvolutionChain: Evolution chain created")

            EvolutionChain(evolutionChainBaseItem)
        } catch (e: Exception) {
            Log.i(
                "PokemonSpeciesResponse",
                "createEvolutionChain: Failure on creating evolution chain - $e"
            )
            createEvolutionChain(evolutionChainClient)
        }

    }
}

data class PokemonSpeciesVarietiesResponse(
    @field:Json(name = "is_default") val isDefault: Boolean,
    val pokemon: BasicResponse
)