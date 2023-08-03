package com.project.mypokedex.sampledata

import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.EvolutionChainItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonGeneration
import com.project.mypokedex.model.PokemonSpecies
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.model.Sprites

val bulbasaur = Pokemon(
    1,
    "bulbasaur",
    listOf(PokemonType.Grass, PokemonType.Poison),
    1,
    Sprites()
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1
    )
}

val charmander = Pokemon(
    4,
    "charmander",
    listOf(PokemonType.Fire),
    4,
    Sprites()
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1
    )
}

val squirtle = Pokemon(
    7,
    "squirtle",
    listOf(PokemonType.Water),
    7,
    Sprites()
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1
    )
}

val charizard = Pokemon(
    6,
    "charizard",
    listOf(PokemonType.Fire, PokemonType.Flying),
    6,
    Sprites()
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1
    )
}

val listPokemons = listOf(bulbasaur, charmander, squirtle)