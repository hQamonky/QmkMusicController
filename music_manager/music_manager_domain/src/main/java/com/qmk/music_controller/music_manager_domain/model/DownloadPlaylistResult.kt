package com.qmk.music_controller.music_manager_domain.model

data class DownloadPlaylistResult(
    val name: String,
    val skippedMusic: List<String>,
    val downloadedMusic: List<String>
)
