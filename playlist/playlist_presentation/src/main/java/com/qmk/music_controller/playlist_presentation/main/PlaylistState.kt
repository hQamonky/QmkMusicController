package com.qmk.music_controller.playlist_presentation.main

import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.playlist_presentation.edit.EditPlaylistState
import com.qmk.music_controller.playlist_presentation.list.LoadingState
import com.qmk.music_controller.playlist_presentation.list.LoadingState.*

data class PlaylistState(
    val processingPlaylist: Playlist? = null,
    val loadingState: LoadingState = STANDBY,
    val customMessage: UiText? = null,
    val editPlaylistState: EditPlaylistState = EditPlaylistState()
)
