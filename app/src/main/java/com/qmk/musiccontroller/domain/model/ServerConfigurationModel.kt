package com.qmk.musiccontroller.domain.model

data class ServerConfigurationModel(
    val url: String,
    val downloadOccurrence: Int,
    val musicFolder: String,
    val autoDownload: Boolean
)
