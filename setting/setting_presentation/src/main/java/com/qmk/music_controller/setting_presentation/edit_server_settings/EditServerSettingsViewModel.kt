package com.qmk.music_controller.setting_presentation.edit_server_settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.ServerInfo
import com.qmk.music_controller.music_manager_domain.model.ServerSettings
import com.qmk.music_controller.music_manager_domain.use_case.settings.GetServerSettings
import com.qmk.music_controller.music_manager_domain.use_case.settings.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditServerSettingsViewModel @Inject constructor(
    private val useCases: SettingsUseCases
): ViewModel() {
    var state by mutableStateOf(EditServerSettingsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val serverSettings = when(val serverSettingsResult = useCases.getServerSettings()) {
                is GetServerSettings.Result.Success -> {
                    serverSettingsResult.serverSettings
                }
                is GetServerSettings.Result.Error -> {
                    null
                }
            }

            val musicDir = serverSettings?.musicFolder ?: ""
            val downloadOccurrence = serverSettings?.downloadOccurrence
            val autoDownload = serverSettings?.autoDownload ?: false

            state = state.copy(
                musicDirectory = musicDir,
                isDlOccurrenceHintVisible = musicDir.isBlank(),
                downloadOccurrence = downloadOccurrence,
                isMusicDirHintVisible = (downloadOccurrence == null),
                autoDownload = autoDownload
            )
        }
    }

    fun onDlOccurrenceEnter(dlOccurrence: String) {
        val intDlOccurrence = dlOccurrence.toIntOrNull()
        state = state.copy(downloadOccurrence = intDlOccurrence)
    }

    fun onDlOccurrenceFocusChange(isFocused: Boolean) {
        state = state.copy(
            isDlOccurrenceHintVisible = !isFocused && (state.downloadOccurrence == null)
        )
    }

    fun onMusicDirEnter(directory: String) {
        state = state.copy(musicDirectory = directory)
    }

    fun onMusicDirFocusChange(isFocused: Boolean) {
        state = state.copy(
            isMusicDirHintVisible = !isFocused && state.musicDirectory.isBlank()
        )
    }

    fun onAutoDlChange(autoDownload: Boolean) {
        state = state.copy(
            autoDownload = autoDownload
        )
    }

    fun onBackClick() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    fun onSaveClick() {
        state = state.copy(
            processState = ProcessState.PROCESSING
        )
        viewModelScope.launch {
            val serverSettings = ServerSettings(
                musicFolder = state.musicDirectory,
                downloadOccurrence = state.downloadOccurrence ?: 0,
                autoDownload = state.autoDownload
            )
            useCases.setServerSettings(serverSettings)
            state = state.copy(
                processState = ProcessState.STANDBY
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}