package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class NamingFormatDto(
    @field:Json(name = "separator")
    val separator: String,
    @field:Json(name = "artist_before_title")
    val aristIsBeforeTitle: Boolean
)
