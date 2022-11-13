package com.qmk.musiccontroller.data.model

data class MusicInfo(
    val id: String,
    val fileName: String,
    val fileExtension: String = "mp3",
    val title: String,
    val artist: String,
    val uploaderId: String,
    val uploadDate: String,
    val isNew: Boolean = true,
    val playlistIds: List<String> = emptyList()
)