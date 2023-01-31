package com.qmk.music_controller.music_manager_domain.use_case.settings

import com.qmk.music_controller.music_manager_domain.model.ServerInfo
import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences

class SetServerInfo(
    private val preferences: MusicManagerPreferences
) {
    operator fun invoke(
        serverInfo: ServerInfo
    ) {
        preferences.saveServerInfo(serverInfo)
    }
}