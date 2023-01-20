package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class PlaylistAddDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "url")
    val url: String
)
