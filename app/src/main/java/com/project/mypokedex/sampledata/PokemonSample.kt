package com.project.mypokedex.sampledata

import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.model.Sprites

val bulbasaur = Pokemon(
    1,
    "bulbasaur",
    listOf(PokemonType.Grass, PokemonType.Poison),
    1,
    Sprites()
)

val charmander = Pokemon(
    4,
    "charmander",
    listOf(PokemonType.Fire),
    4,
    Sprites()
)

val squirtle = Pokemon(
    7,
    "squirtle",
    listOf(PokemonType.Water),
    7,
    Sprites()
)

val charizard = Pokemon(
    6,
    "charizard",
    listOf(PokemonType.Fire, PokemonType.Flying),
    6,
    Sprites()
)

val listPokemons = listOf(bulbasaur, charmander, squirtle)