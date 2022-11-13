package com.qmk.musiccontroller.domain.model

data class PlaylistModel(
    val id: String,
    val name: String,
    val musicIds: List<String> = emptyList()
)