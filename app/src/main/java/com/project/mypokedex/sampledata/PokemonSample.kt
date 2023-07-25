package com.project.mypokedex.sampledata

import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType

val bulbasaur = Pokemon(
    1,
    "bulbasaur",
    listOf(PokemonType.Grass, PokemonType.Poison),
    1
)

val charmander = Pokemon(
    4,
    "charmander",
    listOf(PokemonType.Fire),
    4
)

val squirtle = Pokemon(
    7,
    "squirtle",
    listOf(PokemonType.Water),
    7
)

val charizard = Pokemon(
    6,
    "charizard",
    listOf(PokemonType.Fire, PokemonType.Flying),
    6
)

val listPokemons = listOf(bulbasaur, charmander, squirtle)