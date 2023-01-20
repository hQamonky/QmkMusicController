package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class ServerSettingsDto(
    @field:Json(name = "downloadOccurrence")
    val downloadOccurrence: Int,
    @field:Json(name = "musicFolder")
    val musicFolder: String,
    @field:Json(name = "autoDownload")
    val autoDownload: Boolean
)
