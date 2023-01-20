package com.qmk.music_controller.music_manager_domain.model

data class Channel(
    val id: String,
    val name: String,
    val namingFormat: NamingFormat
)
