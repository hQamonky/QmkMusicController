package com.qmk.music_controller.music_manager_data.repository

import com.qmk.music_controller.music_manager_data.mapper.*
import com.qmk.music_controller.music_manager_data.remote.MusicManagerApi
import com.qmk.music_controller.music_manager_data.remote.dto.NamingRuleDto
import com.qmk.music_controller.music_manager_data.remote.dto.PlaylistAddDto
import com.qmk.music_controller.music_manager_domain.model.*
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class MusicManagerRepositoryImpl(
    private val api: MusicManagerApi
): MusicManagerRepository {
    override suspend fun updateYoutubeDl(): Result<String>  {
        return try {
            val response = api.updateYoutubeDl()
            Result.success(response.body().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun factoryReset(): Result<String> {
        return try {
            val response = api.factoryReset()
            Result.success(response.body().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    // Channels
    override suspend fun getChannels(): Result<List<Channel>> {
        return try {
            val response = api.getChannels()
            Result.success(response.body()?.map { it.toChannel() } ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getChannel(id: String): Result<Channel> {
        return try {
            val response = api.getChannel(id)
            response.body()?.let {
                Result.success(it.toChannel())
            } ?: Result.failure(Exception())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateChannel(channel: Channel): Result<String> {
        return try {
            val response = api.postChannel(channel.id, channel.namingFormat.toNamingFormatDto())
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    // Music
    override suspend fun getNewMusic(): Result<List<Music>> {
        return try {
            val response = api.getNewMusic()
            Result.success(response.body()?.map { it.toMusic() } ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateMusic(music: Music): Result<String> {
        return try {
            val response = api.postMusic(music.id, music.toMusicDto())
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    // Naming Rules
    override suspend fun getNamingRules(): Result<List<NamingRule>> {
        return try {
            val response = api.getNamingRules()
            Result.success(response.body()?.map { it.toNamingRule() } ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getNamingRule(id: String): Result<NamingRule> {
        return try {
            val response = api.getNamingRule(id)
            response.body()?.let {
                Result.success(it.toNamingRule())
            } ?: Result.failure(Exception())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun addNamingRule(
        replace: String,
        replaceBy: String,
        priority: Int
    ): Result<String> {
        return try {
            val newNamingRule = NamingRuleDto("", replace, replaceBy, priority)
            val response = api.postNamingRules(newNamingRule)
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateNamingRule(rule: NamingRule): Result<String> {
        return try {
            val response = api.postNamingRule(rule.id, rule.toNamingRuleDto())
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deleteNamingRule(rule: NamingRule): Result<String> {
        return try {
            val response = api.deleteNamingRule(rule.id)
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    // Playlists
    override suspend fun addPlaylist(name: String, url: String): Result<Playlist> {
        return try {
            val playlistAddDto = PlaylistAddDto(name, url)
            val response = api.postPlaylists(playlistAddDto)
            response.body()?.let {
                Result.success(it.toPlaylist())
            } ?: Result.failure(Exception())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPlaylists(): Result<List<Playlist>> {
        return try {
            val response = api.getPlaylists()
            Result.success(response.body()?.map { it.toPlaylist() } ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPlaylist(id: String): Result<Playlist> {
        return try {
            val response = api.getPlaylist(id)
            response.body()?.let {
                Result.success(it.toPlaylist())
            } ?: Result.failure(Exception())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updatePlaylist(playlist: Playlist): Result<String> {
        return try {
            val response = api.postPlaylist(playlist.id, playlist.toPlaylistDto())
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deletePlaylist(playlist: Playlist): Result<String> {
        return try {
            val response = api.deletePlaylist(playlist.id)
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun downloadPlaylist(id: String): Result<DownloadPlaylistResult> {
        return try {
            val response = api.downloadPlaylist(id)
            response.body()?.let {
                Result.success(it.toDownloadPlaylistResult())
            } ?: Result.failure(Exception())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun downloadPlaylists(): Result<List<DownloadPlaylistResult>> {
        return try {
            val response = api.downloadPlaylists()
            Result.success(
                response.body()?.map {
                    it.toDownloadPlaylistResult()
                } ?: emptyList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun archiveMusic(): Result<List<String>> {
        return try {
            val response = api.archiveMusic()
            Result.success(response.body() ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    // Settings
    override suspend fun getSettings(): Result<ServerSettings> {
        return try {
            val response = api.getSettings()
            response.body()?.let {
                Result.success(it.toServerSettings())
            } ?: Result.failure(Exception())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateSettings(settings: ServerSettings): Result<String> {
        return try {
            val response = api.postSettings(settings.toServerSettingsDto())
            Result.success(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}