package com.qmk.music_controller.setting_presentation.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.use_case.settings.GetServerSettings
import com.qmk.music_controller.music_manager_domain.use_case.settings.SettingsUseCases
import com.qmk.music_controller.core_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplaySettingsViewModel @Inject constructor(
    private val useCases: SettingsUseCases
): ViewModel() {
    var state by mutableStateOf(DisplaySettingsState())
        private set

    init {
        viewModelScope.launch {
            val serverInfo = useCases.getServerInfo()
            val stringPort = serverInfo.port.toString()

            state = state.copy(
                ipAddress = UiText.DynamicString(serverInfo.url),
                portNumber = UiText.DynamicString(stringPort)
            )
        }
        viewModelScope.launch {
            val serverSettings = when(val serverSettingsResult = useCases.getServerSettings()) {
                is GetServerSettings.Result.Success -> {
                    serverSettingsResult.serverSettings
                }
                is GetServerSettings.Result.Error -> {
                    null
                }
            }

            val musicDir = serverSettings?.musicFolder?.let {
                UiText.DynamicString(it)
            } ?: UiText.StringResource(R.string.unknown)
            val downloadOccurrence = serverSettings?.downloadOccurrence?.let {
                UiText.DynamicString(it.toString())
            } ?: UiText.StringResource(R.string.unknown)
            val autoDownload = serverSettings?.autoDownload?.let {
                if (it) {
                    UiText.StringResource(R.string.enabled)
                } else {
                    UiText.StringResource(R.string.disabled)
                }
            } ?: UiText.StringResource(R.string.unknown)

            state = state.copy(
                musicDirectory = musicDir,
                downloadOccurrence = downloadOccurrence,
                autoDownload = autoDownload
            )
        }
    }
}