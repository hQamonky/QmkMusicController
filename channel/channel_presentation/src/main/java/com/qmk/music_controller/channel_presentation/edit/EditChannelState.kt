package com.qmk.music_controller.channel_presentation.edit

import com.qmk.music_controller.core_domain.util.UiText

data class EditChannelState(
    val separator: String = "",
    val isArtistBeforeTitle: Boolean = true,
    val savedResult: UiText? = null
)
