package com.project.mypokedex.model

import androidx.annotation.StringRes
import com.project.mypokedex.R

enum class LanguageUIOption(
    @StringRes val title: Int,
    val languageOption: LanguageOption
) {
    EnglishOption(
        title = R.string.english_language_option,
        languageOption = LanguageOption.English
    ),
    PortugueseOption(
        title = R.string.portuguese_language_option,
        languageOption = LanguageOption.Portuguese
    )
}