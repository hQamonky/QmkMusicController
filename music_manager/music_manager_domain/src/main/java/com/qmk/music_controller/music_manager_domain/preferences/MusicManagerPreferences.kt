package com.qmk.music_controller.music_manager_domain.preferences

import com.qmk.music_controller.music_manager_domain.model.ServerInfo

interface MusicManagerPreferences {
    fun saveServerInfo(info: ServerInfo)
    fun loadServerInfo(): ServerInfo

    companion object {
        const val KEY_SERVER_URL = "server_url"
        const val KEY_SERVER_PORT = "server_port"
    }
}