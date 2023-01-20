package com.qmk.music_controller.music_manager_domain.repository

import com.qmk.music_controller.music_manager_domain.model.*

interface MusicManagerRepository {
    // Main
    suspend fun updateYoutubeDl(): Result<String>
    suspend fun factoryReset(): Result<String>

    // Channels
    suspend fun getChannels(): Result<List<Channel>>
    suspend fun getChannel(id: String): Result<Channel>
    suspend fun updateChannel(channel: Channel): Result<String>

    // Music
    suspend fun getNewMusic(): Result<List<Music>>
    suspend fun updateMusic(music: Music): Result<String>

    // Naming Rules
    suspend fun getNamingRules(): Result<List<NamingRule>>
    suspend fun getNamingRule(id: String): Result<NamingRule>
    suspend fun addNamingRule(replace: String, replaceBy: String, priority: Int): Result<String>
    suspend fun updateNamingRule(rule: NamingRule): Result<String>
    suspend fun deleteNamingRule(rule: NamingRule): Result<String>

    // Playlists
    suspend fun addPlaylist(name: String, url: String): Result<Playlist>
    suspend fun getPlaylists(): Result<List<Playlist>>
    suspend fun getPlaylist(id: String): Result<Playlist>
    suspend fun updatePlaylist(playlist: Playlist): Result<String>
    suspend fun deletePlaylist(playlist: Playlist): Result<String>
    suspend fun downloadPlaylist(id: String): Result<DownloadPlaylistResult>
    suspend fun downloadPlaylists(): Result<List<DownloadPlaylistResult>>
    suspend fun archiveMusic(): Result<List<String>>

    // Settings
    suspend fun getSettings(): Result<ServerSettings>
    suspend fun updateSettings(settings: ServerSettings): Result<String>
}