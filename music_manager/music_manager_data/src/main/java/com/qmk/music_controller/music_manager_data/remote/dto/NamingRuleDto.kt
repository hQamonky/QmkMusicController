package com.qmk.music_controller.music_manager_data.remote.dto

import com.squareup.moshi.Json

data class NamingRuleDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "replace")
    val replace: String,
    @field:Json(name = "replaceBy")
    val replaceBy: String,
    @field:Json(name = "priority")
    val priority: Int
)
