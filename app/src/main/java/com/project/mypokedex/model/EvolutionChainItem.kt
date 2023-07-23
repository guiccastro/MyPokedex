package com.project.mypokedex.model

data class EvolutionChainItem(
    val pokemon: Pokemon,
    val evolvesTo: List<EvolutionChainItem>
)
