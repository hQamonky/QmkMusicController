package com.qmk.musiccontroller.data.model

data class PlaylistInfo(
    val id: String,
    val name: String,
    val musicIds: List<String> = emptyList()
)

data class PlaylistEntry(
    val name: String,
    val url: String
)

data class PlaylistDownloadResult(
    val playlist: String,
    val skipped: MutableList<String> = mutableListOf(),
    val downloaded: MutableList<String> = mutableListOf()
)