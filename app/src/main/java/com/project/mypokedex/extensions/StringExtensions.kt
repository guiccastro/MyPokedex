package com.project.mypokedex.extensions

fun String.getIDFromURL(): Int {
    return this.split("/").dropLast(1).last().toIntOrNull() ?: -1
}