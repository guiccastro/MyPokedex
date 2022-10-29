package com.project.mypokedex.data

class PokemonBaseInfo(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val image: String
) {
    override fun toString(): String {
        return "$id|$name|$types|$image"
    }

    fun typesToString(): String {
        var string = ""
        types.forEachIndexed { index, pokemonType ->
            string += pokemonType.toString()
            if (index != types.size - 1) {
                string += " "
            }
        }
        return string
    }
}