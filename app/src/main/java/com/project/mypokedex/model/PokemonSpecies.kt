package com.project.mypokedex.model

data class PokemonSpecies(
    val evolutionChain: EvolutionChain,
    val varieties: List<Int>,
    val generation: PokemonGeneration?,
    val color: PokemonColor
)
