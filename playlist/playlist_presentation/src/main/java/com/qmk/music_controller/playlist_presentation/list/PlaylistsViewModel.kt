package com.qmk.music_controller.playlist_presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.music_manager_domain.use_case.playlist.GetPlaylists
import com.qmk.music_controller.music_manager_domain.use_case.playlist.PlaylistUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val useCases: PlaylistUseCases
): ViewModel() {
    var state by mutableStateOf(PlaylistsState())
        private set

    init {
        loadPlaylists()
    }

    fun onReloadClick() {
        loadPlaylists()
    }

    private fun loadPlaylists() {
        state = state.copy(loadingState = LoadingState.LOADING)
        viewModelScope.launch {
            state = when(val playlistsResult = useCases.getPlaylists()) {
                is GetPlaylists.Result.Success -> {
                    state.copy(
                        playlists = playlistsResult.playlists,
                        loadingState = LoadingState.STANDBY
                    )
                }
                is GetPlaylists.Result.Error -> {
                    state.copy(loadingState = LoadingState.ERROR)
                }
            }
        }
    }
}