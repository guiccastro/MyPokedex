package com.project.mypokedex.sampledata

import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType

val charizard = Pokemon(
    6,
    "charizard",
    listOf(PokemonType.Fire, PokemonType.Flying),
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/6.gif"
)

val listPokemons = listOf(charizard, charizard, charizard)