package com.project.mypokedex.network.responses

import com.project.mypokedex.model.Sprites

data class PokemonResponse(
    val id: Int,
    val name: String,
    val types: List<TypeListResponse>,
    val sprites: Sprites,
    val species: BasicResponse,
    val height: Int,
    val weight: Int
)

data class TypeListResponse(
    val slot: Int,
    val type: BasicResponse
)
