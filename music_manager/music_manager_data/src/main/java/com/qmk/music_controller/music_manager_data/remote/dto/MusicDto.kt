package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json
import java.util.*

data class MusicDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "fileName")
    val filename: String,
    @field:Json(name = "fileExtension")
    val fileExtension: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "artist")
    val artist: String,
    @field:Json(name = "uploaderId")
    val channelId: String,
    @field:Json(name = "uploadDate")
    val uploadDate: String,
    @field:Json(name = "isNew")
    val isNew: Boolean,
    @field:Json(name = "playlistIds")
    val playlistsId: List<String>
)
