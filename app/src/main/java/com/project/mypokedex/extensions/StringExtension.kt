package com.project.mypokedex.extensions

fun String.firstLetterUppercase(): String {
    return this.replaceFirstChar { it.uppercaseChar() }
}