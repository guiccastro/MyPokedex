package com.project.mypokedex

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.project.mypokedex.model.LanguageOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

private object PreferencesKeys {
    const val DATA_STORE_TITLE = "basic-keys-preferences"
    val BASIC_KEYS = stringPreferencesKey("basic-keys")
    val TOTAL_POKEMONS = intPreferencesKey("total-pokemons")
    val USER_HEIGHT = intPreferencesKey("user-height")
    val GAME_HIGHEST_POINTS = intPreferencesKey("game-highest-points")
    val GAME_CURRENT_POINTS = intPreferencesKey("game-current-points")
    val GAME_CURRENT_OPTIONS = stringPreferencesKey("game-current-options")
    val GAME_ANSWERED = booleanPreferencesKey("game-answered")
    val CURRENT_LANGUAGE = stringPreferencesKey("current-language")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PreferencesKeys.DATA_STORE_TITLE)

suspend fun saveBasicKeysPreferences(context: Context, basicKeys: ArrayList<Pair<String, Int>>) {
    val setString = basicKeys.joinToString(",") { "${it.first}/${it.second}" }
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.BASIC_KEYS] = setString
    }
}

fun getBasicKeysPreferences(context: Context): Flow<List<Pair<String, Int>>> {
    return context.dataStore.data.map { preferences ->
        val stringBasicKeys = preferences[PreferencesKeys.BASIC_KEYS]
        val listBasicKeys = stringBasicKeys?.split(",")?.map {
            val pairValues = it.split("/")
            Pair(pairValues[0], pairValues[1].toInt())
        }
        listBasicKeys ?: emptyList()
    }
}

suspend fun saveTotalPokemonsPreferences(context: Context, totalPokemons: Int) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.TOTAL_POKEMONS] = totalPokemons
    }
}

fun getTotalPokemonsPreferences(context: Context): Flow<Int> {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.TOTAL_POKEMONS] ?: 0
    }
}

suspend fun saveUserHeight(context: Context, height: Int) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.USER_HEIGHT] = height
    }
}

fun getUserHeight(context: Context): Flow<Int> {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_HEIGHT] ?: 150
    }
}

suspend fun saveGameHighestPoints(context: Context, highestPoints: Int) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.GAME_HIGHEST_POINTS] = highestPoints
    }
}

fun getGameHighestPoints(context: Context): Flow<Int> {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.GAME_HIGHEST_POINTS] ?: 0
    }
}

suspend fun saveGameCurrentPoints(context: Context, currentPoints: Int) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.GAME_CURRENT_POINTS] = currentPoints
    }
}

fun getGameCurrentPoints(context: Context): Flow<Int> {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.GAME_CURRENT_POINTS] ?: 0
    }
}

suspend fun saveGameCurrentOptions(context: Context, currentOptions: List<Pair<Boolean, Int>>) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.GAME_CURRENT_OPTIONS] =
            currentOptions.joinToString("|") { "${it.first}/${it.second}" }
    }
}

suspend fun getGameCurrentOptions(context: Context): List<Pair<Boolean, Int>>? {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.GAME_CURRENT_OPTIONS]?.split("|")?.map {
            val pairValues = it.split("/")
            Pair(pairValues[0].toBoolean(), pairValues[1].toInt())
        }
    }.first()
}

suspend fun saveGameAnswered(context: Context, answered: Boolean) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.GAME_ANSWERED] = answered
    }
}

suspend fun getGameAnswered(context: Context): Boolean {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.GAME_ANSWERED] ?: false
    }.first()
}

suspend fun saveCurrentLanguage(context: Context, currentLanguage: Locale) {
    context.dataStore.edit { settings ->
        settings[PreferencesKeys.CURRENT_LANGUAGE] =
            currentLanguage.language + "|" + currentLanguage.country
    }
}

suspend fun getCurrentLanguage(context: Context): Locale? {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.CURRENT_LANGUAGE]?.let {
            val localeValue = it.split("|")
            val language = localeValue.getOrNull(0)
            val country = localeValue.getOrNull(1)
            if (language != null && country != null) {
                Locale(language, country)
            } else {
                LanguageOption.English.locale
            }
        }
    }.first()
}

