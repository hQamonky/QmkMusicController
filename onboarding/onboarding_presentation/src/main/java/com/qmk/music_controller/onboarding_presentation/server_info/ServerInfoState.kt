package com.qmk.music_controller.onboarding_presentation.server_info

data class ServerInfoState(
    val ip: String = "",
    val port: Int? = null,
    val isIPHintVisible: Boolean = false,
    val isPortHintVisible: Boolean = false
)