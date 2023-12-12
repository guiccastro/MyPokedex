package com.project.mypokedex.model

import android.util.Log
import androidx.annotation.StringRes
import com.project.mypokedex.model.downloadInfo.DownloadType
import com.project.mypokedex.model.downloadInfo.UpdateClass
import com.project.mypokedex.model.downloadInfo.UpdateInfo
import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import com.project.mypokedex.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DownloaderInfo(
    private val repository: PokemonRepository,
    private val pokemonClient: PokemonService,
    private val evolutionChainClient: EvolutionChainService,
    private val pokemonSpeciesClient: PokemonSpeciesService,
) {

    companion object {
        private const val MAX_BASIC_KEY_RETRY = 5
        private const val TAG = "DownloaderInfo"
    }

    private val requestPokemons = ArrayList<Int>()
    var progressRequest: MutableStateFlow<Float> = MutableStateFlow(0F)
    var needToRequestPokemons: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    var pokemonDownloadInfo: DownloadType = DownloadType.None

    @StringRes
    var pokemonPropertiesDesc: List<Int> = emptyList()
    private var updateClassToDownload: List<UpdateClass> = emptyList()

    private var onFailureCountBasicKeys = 0

    private fun logStatus(message: String) {
        Log.i(TAG, message)
    }

    suspend fun requestBasicKeys(): BasicKeysResponse? {
        try {
            logStatus("getBasicKeys: Requesting Basic Keys")
            val basicKeys = pokemonClient.getBasicKeys()
            logStatus("onResponse: Basic Keys")
            return basicKeys
        } catch (e: Exception) {
            onFailureCountBasicKeys++
            logStatus("onFailure: Basic Keys - Failures: $onFailureCountBasicKeys - $e")
            if (onFailureCountBasicKeys < MAX_BASIC_KEY_RETRY) {
                requestBasicKeys()
            } else {
                return null
            }
        }

        return null
    }

    fun prepareToDownload(pokemonList: List<Pokemon>, totalPokemons: Int) {
        pokemonDownloadInfo = DownloadType.getDownloadType(pokemonList, totalPokemons)
        pokemonPropertiesDesc = UpdateInfo.getDescription(pokemonList)
        needToRequestPokemons.value = pokemonDownloadInfo != DownloadType.None

        if (needToRequestPokemons.value == false) {
            logStatus("verifyDaoData: Pokemon List read from DAO")
            progressRequest.value = 1F
        }
    }

    suspend fun requestPokemons(
        pokemonBasicKeys: List<Int>,
        pokemonList: List<Pokemon>,
        totalPokemons: Int
    ) {
        setRequestPokemons(
            pokemonBasicKeys,
            pokemonList
        )
        requestAllPokemons(totalPokemons)
    }

    private fun setRequestPokemons(pokemonBasicKeys: List<Int>, pokemonList: List<Pokemon>) {
        requestPokemons.addAll(
            pokemonDownloadInfo.getRequestPokemons(
                pokemonBasicKeys,
                pokemonList
            )
        )

        updateClassToDownload = UpdateClass.getClasses(pokemonList)
    }

    private suspend fun requestAllPokemons(totalPokemons: Int) {
        var responseCount = 0
        val toIndex = if (requestPokemons.size >= totalPokemons) {
            totalPokemons
        } else {
            requestPokemons.size
        }
        CoroutineScope(Dispatchers.Main).launch {
            requestPokemons.subList(0, toIndex).forEach { id ->
                async {
                    try {
                        requestInfo(id)
                        requestPokemons.remove(id)
                        calculateProgressRequest()
                        responseCount++
                    } catch (e: Exception) {
                        logStatus("onFailure: Pokemon - $id - $e")
                    }
                }
            }
        }.join()

        if (progressRequest.value != 1F) {
            requestAllPokemons(totalPokemons)
        }
    }

    private suspend fun requestInfo(id: Int) {
        if (pokemonDownloadInfo == DownloadType.FullInfo) {
            requestPokemonFull(id)
        } else if (pokemonDownloadInfo == DownloadType.NewInfo) {
            updateClassToDownload.forEach { updateClass ->
                when (updateClass) {
                    UpdateClass.PokemonClass -> requestOnlyPokemonInfo(id)
                    UpdateClass.PokemonSpecies -> requestOnlyPokemonSpecies(id)
                }
            }
        }
    }

    private suspend fun requestPokemonFull(id: Int) {
        try {
            logStatus("requestPokemonFull: Requesting pokemon $id from client")
            val pokemonResponse = pokemonClient.getPokemon(id)
            logStatus("requestPokemonFull: Get pokemon $id from client")

            val pokemon = pokemonResponse.createPokemon().apply {
                species = getPokemonSpecies(speciesId)
            }
            repository.addPokemon(pokemon)
        } catch (e: Exception) {
            logStatus("requestPokemonFull: Failure on requesting pokemon $id - $e")
            requestPokemonFull(id)
        }
    }

    private suspend fun requestOnlyPokemonInfo(id: Int) {
        try {
            logStatus("requestOnlyPokemonInfo: Requesting pokemon $id from client")
            val pokemonResponse = pokemonClient.getPokemon(id)
            logStatus("requestOnlyPokemonInfo: Get pokemon $id from client")
            val newPokemon = pokemonResponse.createPokemon()
            val oldPokemon = repository.getPokemon(id)
            newPokemon.apply {
                species = oldPokemon?.species
            }
            repository.replacePokemon(newPokemon)
        } catch (e: Exception) {
            logStatus("requestOnlyPokemonInfo: Failure on requesting pokemon $id - $e")
            requestOnlyPokemonInfo(id)
        }
    }

    private suspend fun requestOnlyPokemonSpecies(id: Int) {
        try {
            val oldPokemon = repository.getPokemon(id) ?: return
            val speciesId = oldPokemon.speciesId
            val newSpecies = getPokemonSpecies(speciesId)
            val newPokemon = oldPokemon.apply {
                species = newSpecies
            }
            repository.replacePokemon(newPokemon)
        } catch (e: Exception) {
            logStatus("requestOnlyPokemonSpecies: Failure on requesting pokemon species $id - $e")
            requestOnlyPokemonSpecies(id)
        }
    }

    private suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
        return try {
            logStatus("getPokemonSpecies: Requesting pokemon species $id from client")
            val pokemonSpeciesResponse = pokemonSpeciesClient.getPokemonSpecies(id)
            logStatus("getPokemonSpecies: Get pokemon species $id from client")
            pokemonSpeciesResponse.createPokemonSpecies(evolutionChainClient)
        } catch (e: Exception) {
            logStatus("getPokemonSpecies: Failure on requesting pokemon species $id - $e")
            getPokemonSpecies(id)
        }

    }

    private fun calculateProgressRequest() {
        progressRequest.value =
            1F - (requestPokemons.size.toFloat() / repository.getTotalPokemons().toFloat())
        logStatus("ProgressRequest: ${progressRequest.value}%")
    }
}