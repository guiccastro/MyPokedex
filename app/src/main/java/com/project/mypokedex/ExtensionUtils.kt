package com.project.mypokedex

import com.project.mypokedex.data.PokemonInfo

fun String.firstLetterUppercase(): String {
    return this.replaceFirstChar { it.uppercaseChar() }
}

fun PokemonInfo.getTypesString(): String {
    var text = ""
    types.forEach {
        text = "$text${it.type?.name?.firstLetterUppercase()} "
    }
    return text
}