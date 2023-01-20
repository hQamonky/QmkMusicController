package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class PlaylistDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "musicIds")
    val musicIds: List<String>
)
