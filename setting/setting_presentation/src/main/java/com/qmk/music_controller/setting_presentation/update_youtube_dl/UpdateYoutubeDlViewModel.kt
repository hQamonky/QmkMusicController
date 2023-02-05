package com.qmk.music_controller.setting_presentation.update_youtube_dl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.use_case.settings.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateYoutubeDlViewModel @Inject constructor(
    private val useCases: SettingsUseCases
): ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onConfirm() {
        isLoading = true
        viewModelScope.launch {
            useCases.updateYoutubeDl()
            _uiEvent.send(UiEvent.NavigateUp)
            isLoading = false
        }
    }
}