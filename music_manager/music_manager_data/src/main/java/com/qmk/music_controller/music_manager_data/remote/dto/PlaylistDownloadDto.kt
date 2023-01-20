package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class PlaylistDownloadDto(
    @field:Json(name = "playlist")
    val name: String,
    @field:Json(name = "skipped")
    val skippedMusic: List<String>,
    @field:Json(name = "downloaded")
    val downloadedMusic: List<String>
)
