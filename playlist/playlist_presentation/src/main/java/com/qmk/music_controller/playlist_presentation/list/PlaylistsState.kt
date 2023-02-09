package com.qmk.music_controller.playlist_presentation.list

import com.qmk.music_controller.music_manager_domain.model.Playlist

data class PlaylistsState(
    val playlists: List<Playlist> = emptyList(),
    val loadingState: LoadingState = LoadingState.STANDBY
)

enum class LoadingState(val message: String?) {
    STANDBY(null),
    LOADING(null),
    ERROR(null)
}
