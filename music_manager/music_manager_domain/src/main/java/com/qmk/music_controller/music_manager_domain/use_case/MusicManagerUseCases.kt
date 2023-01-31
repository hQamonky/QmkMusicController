package com.qmk.music_controller.music_manager_domain.use_case

import com.qmk.music_controller.music_manager_domain.use_case.playlist.PlaylistUseCases
import com.qmk.music_controller.music_manager_domain.use_case.settings.SettingsUseCases

data class MusicManagerUseCases(
    val playlistUseCases: PlaylistUseCases,
    val settingsUseCases: SettingsUseCases
)