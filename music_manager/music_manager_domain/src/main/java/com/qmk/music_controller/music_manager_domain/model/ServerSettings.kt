package com.qmk.music_controller.music_manager_domain.model

data class ServerSettings(
    val downloadOccurrence: Int,
    val musicFolder: String,
    val autoDownload: Boolean
)
