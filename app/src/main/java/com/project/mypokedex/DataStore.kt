package com.project.mypokedex

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private object PreferencesKeys {
    const val DATA_STORE_TITLE = "basic-keys-preferences"
    val BASIC_KEYS = stringPreferencesKey("basic-keys")
    val TOTAL_POKEMONS = intPreferencesKey("total-pokemons")
    val USER_HEIGHT = intPreferencesKey("user-height")
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

