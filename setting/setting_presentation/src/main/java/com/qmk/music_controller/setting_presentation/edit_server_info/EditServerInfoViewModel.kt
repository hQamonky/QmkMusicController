package com.qmk.music_controller.setting_presentation.edit_server_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.ServerInfo
import com.qmk.music_controller.music_manager_domain.use_case.settings.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditServerInfoViewModel @Inject constructor(
    private val useCases: SettingsUseCases
): ViewModel() {
    var state by mutableStateOf(EditServerInfoState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val serverInfo = useCases.getServerInfo()
        state = state.copy(
            ipAddress = serverInfo.url,
            portNumber = serverInfo.port
        )
    }

    fun onIPEnter(ip: String) {
        state = state.copy(ipAddress = ip)
    }

    fun onIPFocusChange(isFocused: Boolean) {
        state = state.copy(
            isIpHintVisible = !isFocused && state.ipAddress.isBlank()
        )
    }

    fun onPortEnter(port: String) {
        val intPort = port.toIntOrNull()
        state = state.copy(portNumber = intPort)
    }

    fun onPortFocusChange(isFocused: Boolean) {
        state = state.copy(
            isPortHintVisible = !isFocused && (state.portNumber == null)
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
            val serverInfo = ServerInfo(
                url = state.ipAddress,
                port = state.portNumber
            )
            useCases.setServerInfo(serverInfo)
            state = state.copy(
                processState = ProcessState.STANDBY
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}