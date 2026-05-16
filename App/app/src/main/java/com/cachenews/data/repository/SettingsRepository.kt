package com.cachenews.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cachenews.domain.model.AppLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_LANGUAGE = stringPreferencesKey("selected_language")
        private val KEY_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private val KEY_GEMINI_API_KEY = stringPreferencesKey("gemini_api_key")
    }

    val selectedLanguage: Flow<AppLanguage> = context.dataStore.data.map { prefs ->
        val code = prefs[KEY_LANGUAGE] ?: AppLanguage.ENGLISH.code
        AppLanguage.entries.find { it.code == code } ?: AppLanguage.ENGLISH
    }

    val isFirstLaunch: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_FIRST_LAUNCH] ?: true
    }

    val geminiApiKey: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_GEMINI_API_KEY]
    }

    suspend fun setLanguage(language: AppLanguage) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LANGUAGE] = language.code
            prefs[KEY_FIRST_LAUNCH] = false
        }
    }

    suspend fun saveGeminiApiKey(apiKey: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_GEMINI_API_KEY] = apiKey
        }
    }

    suspend fun deleteGeminiApiKey() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_GEMINI_API_KEY)
        }
    }

    suspend fun getGeminiApiKeySync(): String? {
        return context.dataStore.data.map { it[KEY_GEMINI_API_KEY] }.first()
    }

    suspend fun getLanguageSync(): AppLanguage {
        var lang = AppLanguage.ENGLISH
        context.dataStore.data.collect { prefs ->
            val code = prefs[KEY_LANGUAGE] ?: AppLanguage.ENGLISH.code
            lang = AppLanguage.entries.find { it.code == code } ?: AppLanguage.ENGLISH
        }
        return lang
    }
}
