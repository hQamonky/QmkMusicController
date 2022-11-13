package com.qmk.musiccontroller.domain.controller

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.qmk.musiccontroller.data.RetrofitHelper
import com.qmk.musiccontroller.data.api.SettingsAPI
import com.qmk.musiccontroller.data.model.SettingsInfo
import com.qmk.musiccontroller.domain.model.ServerConfigurationModel
import kotlinx.coroutines.flow.*
import java.io.IOException


data class UserPreferences(
    val server_url: String
)

class SettingsController(private val dataStore: DataStore<Preferences>) {
    private val tag = "SettingsController"
    private val settingsAPI = RetrofitHelper.getInstance().create(SettingsAPI::class.java)

    private object PreferencesKeys {
        val SERVER_URL = stringPreferencesKey("server_url")
    }

    /**
     * Get the user preferences flow.
     */
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(tag, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateServerUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SERVER_URL] = url
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    suspend fun getServerConfiguration(): ServerConfigurationModel? {
        val configuration = settingsAPI.getSettings().body()
        val userPreferences = mapUserPreferences(dataStore.data.last().toPreferences())
        return configuration?.let {
            ServerConfigurationModel(
                userPreferences.server_url,
                it.downloadOccurrence,
                it.musicFolder,
                it.autoDownload
            )
        }
    }

    suspend fun editServerConfiguration(configuration: ServerConfigurationModel) {
        settingsAPI.postSettings(SettingsInfo(
            configuration.downloadOccurrence,
            configuration.musicFolder,
            configuration.autoDownload
        ))
        updateServerUrl(configuration.url)
    }

    suspend fun resetDefaultServerConfiguration() {
        settingsAPI.factoryReset()
    }

    suspend fun updateYoutubeDl() {
        settingsAPI.updateYoutubeDl()
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        // Get the server url from preferences
        val serverUrl = preferences[PreferencesKeys.SERVER_URL] ?: ""
        return UserPreferences(serverUrl)
    }
}