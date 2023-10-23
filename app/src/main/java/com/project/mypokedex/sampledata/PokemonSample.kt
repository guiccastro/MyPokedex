package com.project.mypokedex.sampledata

import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.EvolutionChainItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonColor
import com.project.mypokedex.model.PokemonGeneration
import com.project.mypokedex.model.PokemonSpecies
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.model.Sprites

val bulbasaur = Pokemon(
    1,
    "bulbasaur",
    listOf(PokemonType.Grass, PokemonType.Poison),
    1,
    Sprites(),
    10
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1,
        color = PokemonColor.Green
    )
}

val charmander = Pokemon(
    4,
    "charmander",
    listOf(PokemonType.Fire),
    4,
    Sprites(),
    10
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1,
        color = PokemonColor.Yellow
    )
}

val squirtle = Pokemon(
    7,
    "squirtle",
    listOf(PokemonType.Water),
    7,
    Sprites(),
    10
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1,
        color = PokemonColor.Blue
    )
}

val charizard = Pokemon(
    6,
    "charizard",
    listOf(PokemonType.Fire, PokemonType.Flying),
    6,
    Sprites(),
    10
).apply {
    species = PokemonSpecies(
        evolutionChain = EvolutionChain(EvolutionChainItem(1, emptyList())),
        varieties = emptyList(),
        generation = PokemonGeneration.Gen1,
        color = PokemonColor.Red
    )
}

val listPokemons = listOf(bulbasaur, charmander, squirtle)