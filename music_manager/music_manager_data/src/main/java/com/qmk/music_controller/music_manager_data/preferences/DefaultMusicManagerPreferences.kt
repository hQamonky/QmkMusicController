package com.qmk.music_controller.music_manager_data.preferences

import android.content.SharedPreferences
import com.qmk.music_controller.music_manager_domain.model.ServerInfo
import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences

class DefaultMusicManagerPreferences(
    private val sharedPref: SharedPreferences
): MusicManagerPreferences {
    override fun saveServerInfo(info: ServerInfo) {
        val stringPort = info.port?.toString() ?: ""
        sharedPref.edit()
            .putString(MusicManagerPreferences.KEY_SERVER_URL, info.url)
            .putString(MusicManagerPreferences.KEY_SERVER_PORT, stringPort)
            .apply()
    }

    override fun loadServerInfo(): ServerInfo {
        val url = sharedPref.getString(MusicManagerPreferences.KEY_SERVER_URL, null)
        val port = sharedPref
            .getString(MusicManagerPreferences.KEY_SERVER_PORT, null)?.toIntOrNull()
        return ServerInfo(
            url = url ?: "",
            port = port
        )
    }
}