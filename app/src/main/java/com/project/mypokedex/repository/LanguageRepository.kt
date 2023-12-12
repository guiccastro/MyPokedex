package com.project.mypokedex.repository

import android.app.LocaleManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.project.mypokedex.model.LanguageOption
import com.project.mypokedex.saveCurrentLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.Locale

object LanguageRepository {

    private var currentLanguage = LanguageOption.English.locale

    fun getCurrentLanguage(): Locale = currentLanguage

    fun initiateLanguage(context: Context) {
        CoroutineScope(IO).launch {
            val locale =
                com.project.mypokedex.getCurrentLanguage(context)
                    ?: Resources.getSystem().configuration.locales.get(0)
            changeLanguage(context, locale)
        }
    }

    fun changeLanguage(context: Context, locale: Locale) {
        if (LanguageOption.values().map { it.locale }.contains(locale)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList.forLanguageTags(locale.toLanguageTag())
            } else {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(locale.toLanguageTag())
                )
            }
            currentLanguage = locale
            CoroutineScope(IO).launch {
                saveCurrentLanguage(context, locale)
            }
        }
    }
}