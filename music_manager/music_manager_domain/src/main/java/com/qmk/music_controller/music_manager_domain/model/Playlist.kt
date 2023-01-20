package com.qmk.music_controller.music_manager_domain.model

data class Playlist(
    val id: String,
    val name: String,
    val musicIds: List<String>
)
