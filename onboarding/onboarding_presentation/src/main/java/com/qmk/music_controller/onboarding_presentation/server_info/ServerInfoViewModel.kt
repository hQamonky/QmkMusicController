package com.qmk.music_controller.onboarding_presentation.server_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.ServerInfo
import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerInfoViewModel @Inject constructor(
    private val preferences: MusicManagerPreferences
): ViewModel() {
    var state by mutableStateOf(ServerInfoState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val serverInfo = preferences.loadServerInfo()
        state = state.copy(
            ip = serverInfo.url,
            port = serverInfo.port
        )
    }

    fun onIPEnter(ip: String) {
        state = state.copy(ip = ip)
    }

    fun onIPFocusChange(isFocused: Boolean) {
        state = state.copy(
            isIPHintVisible = !isFocused && state.ip.isBlank()
        )
    }

    fun onPortEnter(port: String) {
        val intPort = port.toIntOrNull()
        state = state.copy(port = intPort)
    }

    fun onPortFocusChange(isFocused: Boolean) {
        state = state.copy(
            isPortHintVisible = !isFocused && (state.port == null)
        )
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveServerInfo(ServerInfo(
                url = state.ip,
                port = state.port
            ))
            _uiEvent.send(UiEvent.Success)
        }
    }
}