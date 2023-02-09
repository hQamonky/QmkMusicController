package com.qmk.music_controller.playlist_presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.music_manager_domain.use_case.playlist.DeletePlaylist
import com.qmk.music_controller.music_manager_domain.use_case.playlist.GetPlaylist
import com.qmk.music_controller.music_manager_domain.use_case.playlist.PlaylistUseCases
import com.qmk.music_controller.playlist_presentation.R
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.ERROR
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.LIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.LOADING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val useCases: PlaylistUseCases
): ViewModel() {
    var state by mutableStateOf(PlaylistState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getPlaylist(id: String, onProcessSuccess: (playlist: Playlist) -> Unit) {
        viewModelScope.launch {
            state = state.copy(customMessage = UiText.StringResource(
                R.string.loading_get_playlist_info))
            _uiEvent.send(UiEvent.NavigateTo(LOADING))
            when(val result = useCases.getPlaylist(id)) {
                is GetPlaylist.Result.Success -> {
                    onProcessSuccess(result.playlist)
                }
                is GetPlaylist.Result.Error -> {
                    state = state.copy(customMessage = result.message)
                    _uiEvent.send(UiEvent.NavigateTo(ERROR))
                }
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            state = state.copy(customMessage = null)
            _uiEvent.send(UiEvent.NavigateTo(LOADING))
            when(val result = useCases.deletePlaylist(playlist)) {
                is DeletePlaylist.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.playlist_deleted)))
                }
                is DeletePlaylist.Result.Error -> {
//                    state = state.copy(customMessage = result.message)
//                    _uiEvent.send(UiEvent.NavigateTo(ERROR))
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                }
            }
        }
    }
}