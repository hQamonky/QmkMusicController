package com.qmk.music_controller.music_manager_data.mapper

import com.qmk.music_controller.music_manager_data.remote.dto.PlaylistDownloadDto
import com.qmk.music_controller.music_manager_data.remote.dto.PlaylistDto
import com.qmk.music_controller.music_manager_domain.model.DownloadPlaylistResult
import com.qmk.music_controller.music_manager_domain.model.Playlist

fun PlaylistDto.toPlaylist(): Playlist {
    return Playlist(
        id = id,
        name = name,
        musicIds = musicIds
    )
}

fun Playlist.toPlaylistDto(): PlaylistDto {
    return PlaylistDto(
        id = id,
        name = name,
        musicIds = musicIds
    )
}

fun PlaylistDownloadDto.toDownloadPlaylistResult(): DownloadPlaylistResult {
    return DownloadPlaylistResult(
        name = name,
        skippedMusic = skippedMusic,
        downloadedMusic = downloadedMusic
    )
}