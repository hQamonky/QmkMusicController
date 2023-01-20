package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class ChannelDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "namingFormat")
    val namingFormat: NamingFormatDto
)
