package com.qmk.music_controller.playlist_presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.music_manager_domain.use_case.playlist.*
import com.qmk.music_controller.playlist_presentation.R
import com.qmk.music_controller.playlist_presentation.list.LoadingState
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.LIST
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

    fun downloadPlaylists() {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            when(val result = useCases.downloadPlaylists()) {
                is DownloadPlaylists.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.playlists_downloaded)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is DownloadPlaylists.Result.Error -> {
//                    state = state.copy(customMessage = result.message)
//                    _uiEvent.send(UiEvent.NavigateTo(ERROR))
                    viewModelScope.launch {
                        state = state.copy(
                            loadingState = LoadingState.STANDBY,
                            customMessage = result.message
                        )
                        _uiEvent.send(UiEvent.NavigateTo(LIST))
                        _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                    }
                }
            }
        }
    }

    private suspend fun getPlaylist(id: String, onProcessSuccess: (playlist: Playlist) -> Unit) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = UiText.StringResource(
                R.string.loading_get_playlist_info)
        )
        when(val result = useCases.getPlaylist(id)) {
            is GetPlaylist.Result.Success -> {
                state = state.copy(
                    loadingState = LoadingState.STANDBY,
                    customMessage = null,
                    processingPlaylist = result.playlist
                )
                onProcessSuccess(result.playlist)
            }
            is GetPlaylist.Result.Error -> {
                state = state.copy(
                    loadingState = LoadingState.ERROR,
                    customMessage = result.message,
                    processingPlaylist = null
                )
            }
        }
    }


    // Edit Playlist -----------------------------


    fun getPlaylistForEdit(id: String) {
        viewModelScope.launch {
            getPlaylist(id) {
                state = state.copy(
                    editPlaylistState = state.editPlaylistState.copy(
                        name = it.name
                    )
                )
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(PlaylistRoute.EDIT_PLAYLIST))
                }
            }
        }
    }

    fun onEditNameEnter(name: String) {
        state = state.copy(
            editPlaylistState = state.editPlaylistState.copy(
                name = name,
                isNameHintVisible = name.isBlank()
            )
        )
    }

    fun onEditNameFocusChange(isFocused: Boolean) {
        state = state.copy(
            editPlaylistState = state.editPlaylistState.copy(
                isNameHintVisible = !isFocused && state.editPlaylistState.name.isBlank()
            )
        )
    }

    fun editPlaylist(playlist: Playlist) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            val newPlaylist = playlist.copy(name = state.editPlaylistState.name)
            when(val result = useCases.updatePlaylist(newPlaylist)) {
                is UpdatePlaylist.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.playlist_updated)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is UpdatePlaylist.Result.Error -> {
//                    state = state.copy(
//                        loadingState = LoadingState.ERROR,
//                        customMessage = result.message
//                    )
                    viewModelScope.launch {
                        state = state.copy(
                            loadingState = LoadingState.STANDBY,
                            customMessage = result.message
                        )
                        _uiEvent.send(UiEvent.NavigateTo(LIST))
                        _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                    }
                }
            }
        }
    }


    // Delete Playlist -----------------------------


    fun getPlaylistForDelete(id: String) {
        viewModelScope.launch {
            getPlaylist(id) {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(PlaylistRoute.DELETE_PLAYLIST))
                }
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            when(val result = useCases.deletePlaylist(playlist)) {
                is DeletePlaylist.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.playlist_deleted)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is DeletePlaylist.Result.Error -> {
//                    state = state.copy(customMessage = result.message)
//                    _uiEvent.send(UiEvent.NavigateTo(ERROR))
                    viewModelScope.launch {
                        state = state.copy(
                            loadingState = LoadingState.STANDBY,
                            customMessage = result.message
                        )
                        _uiEvent.send(UiEvent.NavigateTo(LIST))
                        _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                    }
                }
            }
        }
    }


    // Download Playlist -----------------------------


    fun getPlaylistForDownload(id: String) {
        viewModelScope.launch {
            getPlaylist(id) {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(PlaylistRoute.DOWNLOAD_PLAYLIST))
                }
            }
        }
    }

    fun downloadPlaylist(playlist: Playlist) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            when(val result = useCases.downloadPlaylist(playlist)) {
                is DownloadPlaylist.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.playlist_downloaded)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is DownloadPlaylist.Result.Error -> {
//                    state = state.copy(customMessage = result.message)
//                    _uiEvent.send(UiEvent.NavigateTo(ERROR))
                    viewModelScope.launch {
                        state = state.copy(
                            loadingState = LoadingState.STANDBY,
                            customMessage = result.message
                        )
                        _uiEvent.send(UiEvent.NavigateTo(LIST))
                        _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                    }
                }
            }
        }
    }
}