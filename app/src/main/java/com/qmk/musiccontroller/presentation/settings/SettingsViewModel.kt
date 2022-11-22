package com.qmk.musiccontroller.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.musiccontroller.domain.controller.SettingsManager
import com.qmk.musiccontroller.domain.model.ServerConfigurationModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager
): ViewModel() {

    val serverConfigurationFlow: StateFlow<ServerConfigurationModel?>
        get() = flow {
            emit(settingsManager.getServerConfiguration())
        }.stateIn(viewModelScope, SharingStarted.Lazily, ServerConfigurationModel(
            "",
            1,
            "",
            true
        ))

    fun setServerUrl(url: String) {
        viewModelScope.launch {
            settingsManager.updateServerUrl(url)
        }
    }

    fun setDownloadOccurrence(downloadOccurrence: Int) {
        viewModelScope.launch {
            serverConfigurationFlow
                .last()?.copy(downloadOccurrence = downloadOccurrence)?.let {
                    settingsManager.editServerConfiguration(it)
                }
        }
    }

    fun setMusicFolder(musicFolder: String) {
        viewModelScope.launch {
            serverConfigurationFlow
                .last()?.copy(musicFolder = musicFolder)?.let {
                    settingsManager.editServerConfiguration(it)
                }
        }
    }

    fun setAutoDownload(autoDownload: Boolean) {
        viewModelScope.launch {
            serverConfigurationFlow
                .last()?.copy(autoDownload = autoDownload)?.let {
                    settingsManager.editServerConfiguration(it)
                }
        }
    }
}