package com.qmk.music_controller.music_manager_data.mapper

import com.qmk.music_controller.music_manager_data.remote.dto.ServerSettingsDto
import com.qmk.music_controller.music_manager_domain.model.ServerSettings

fun ServerSettingsDto.toServerSettings(): ServerSettings {
    return ServerSettings(
        downloadOccurrence = downloadOccurrence,
        musicFolder = musicFolder,
        autoDownload = autoDownload
    )
}

fun ServerSettings.toServerSettingsDto(): ServerSettingsDto {
    return ServerSettingsDto(
        downloadOccurrence = downloadOccurrence,
        musicFolder = musicFolder,
        autoDownload = autoDownload
    )
}