package com.project.mypokedex

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.project.mypokedex.model.LanguageOption
import java.util.Locale

object LanguageCentral {

    private var currentLanguage = LanguageOption.English.locale

    fun getCurrentLanguage(): Locale = currentLanguage

    fun changeLanguage(context: Context, locale: Locale) {
        currentLanguage = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(locale.toLanguageTag())
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(locale.toLanguageTag())
            )
        }
    }
}