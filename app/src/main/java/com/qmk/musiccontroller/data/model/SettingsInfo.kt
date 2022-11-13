package com.qmk.musiccontroller.data.model

data class SettingsInfo(
    val downloadOccurrence: Int = 1,
    val musicFolder: String = "./Music",
    val autoDownload: Boolean = true
)