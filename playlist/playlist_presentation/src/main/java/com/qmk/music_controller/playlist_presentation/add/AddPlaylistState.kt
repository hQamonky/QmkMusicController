package com.qmk.music_controller.playlist_presentation.add

import com.qmk.music_controller.core_domain.util.UiText

data class AddPlaylistState(
    val url: String = "",
    val name: String = "",
    val isUrlHintVisible: Boolean = false,
    val isNameHintVisible: Boolean = false,
    val processState: ProcessState = ProcessState.ENTERING_FIELDS,
    val savedResult: UiText? = null
)

enum class ProcessState {
    ENTERING_FIELDS,
    PROCESSING,
    PLAYLIST_SAVED,
    ERROR_SAVING_PLAYLIST
}
