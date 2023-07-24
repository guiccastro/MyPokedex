package com.project.mypokedex.sampledata

import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType

val bulbasaur = Pokemon(
    1,
    "bulbasaur",
    listOf(PokemonType.Grass, PokemonType.Poison),
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/1.gif",
    "",
    1
)

val charmander = Pokemon(
    4,
    "charmander",
    listOf(PokemonType.Fire),
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/4.gif",
    "",
    4
)

val squirtle = Pokemon(
    7,
    "squirtle",
    listOf(PokemonType.Water),
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/7.gif",
    "",
    7
)

val charizard = Pokemon(
    6,
    "charizard",
    listOf(PokemonType.Fire, PokemonType.Flying),
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/6.gif",
    "",
    6
)

val listPokemons = listOf(bulbasaur, charmander, squirtle)