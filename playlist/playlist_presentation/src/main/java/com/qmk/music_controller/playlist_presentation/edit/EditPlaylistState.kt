package com.qmk.music_controller.playlist_presentation.edit

import com.qmk.music_controller.core_domain.util.UiText

data class EditPlaylistState(
    val name: String = "",
    val isNameHintVisible: Boolean = false,
    val savedResult: UiText? = null
)
