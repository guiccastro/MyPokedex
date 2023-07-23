package com.project.mypokedex.ui.extensions

fun String.getIDFromURL(): Int {
    return this.split("/").dropLast(1).last().toIntOrNull() ?: -1
}