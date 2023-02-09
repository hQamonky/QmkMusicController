package com.qmk.music_controller.playlist_presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.use_case.playlist.AddPlaylist
import com.qmk.music_controller.music_manager_domain.use_case.playlist.PlaylistUseCases
import com.qmk.music_controller.playlist_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaylistViewModel @Inject constructor(
    private val useCases: PlaylistUseCases
): ViewModel() {

    var state by mutableStateOf(AddPlaylistState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUrlEnter(url: String) {
        state = state.copy(url = url)
    }

    fun onNameEnter(name: String) {
        state = state.copy(name = name)
    }

    fun onUrlFocusChange(isFocused: Boolean) {
        state = state.copy(
            isUrlHintVisible = !isFocused && state.url.isBlank()
        )
    }

    fun onNameFocusChange(isFocused: Boolean) {
        state = state.copy(
            isNameHintVisible = !isFocused && state.name.isBlank()
        )
    }

    fun onSaveClick() {
        state = state.copy(processState = ProcessState.PROCESSING)
        viewModelScope.launch {
            val result = useCases.addPlaylist(state.name, state.url)
            state = state.copy(
                savedResult = when(result) {
                    is AddPlaylist.Result.Success -> UiText.StringResource(R.string.playlist_added)
                    is AddPlaylist.Result.Error -> result.message
                },
                processState = when(result) {
                    is AddPlaylist.Result.Success -> ProcessState.PLAYLIST_SAVED
                    is AddPlaylist.Result.Error -> ProcessState.ERROR_SAVING_PLAYLIST
                }
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}