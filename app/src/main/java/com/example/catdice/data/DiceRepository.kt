package com.example.catdice.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.catdice.domain.RollResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dice_prefs")

@Singleton
class DiceRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val historyKey = stringPreferencesKey("history_list")

    val historyFlow: Flow<List<RollResult>> = context.dataStore.data.map { preferences ->
        val json = preferences[historyKey]
        if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<RollResult>>() {}.type
            gson.fromJson(json, type)
        }
    }

    suspend fun saveHistory(list: List<RollResult>) {
        val json = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[historyKey] = json
        }
    }
}