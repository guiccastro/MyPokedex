package com.project.mypokedex.model

data class EvolutionChainItem(
    val pokemonId: Int,
    val evolvesTo: List<EvolutionChainItem>
)
