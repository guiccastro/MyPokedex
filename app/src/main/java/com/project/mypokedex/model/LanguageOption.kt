package com.project.mypokedex.model

import java.util.Locale

enum class LanguageOption(
    val locale: Locale
) {
    English(
        locale = Locale("en", "US")
    ),
    Portuguese(
        locale = Locale("pt", "BR")
    )
}