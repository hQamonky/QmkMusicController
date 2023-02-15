package com.qmk.music_controller.music_manager_domain.model

import java.util.*

data class Music(
    val id: String,
    val filename: String,
    val fileExtension: String,
    val title: String,
    val artist: String,
    val channelId: String,
    val uploadDate: String,
    val isNew: Boolean,
    val playlistsId: List<String>
)
