package com.project.mypokedex

fun String.firstLetterUppercase(): String {
    return this.replaceFirstChar { it.uppercaseChar() }
}