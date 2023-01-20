package com.qmk.music_controller.music_manager_data.mapper

import com.qmk.music_controller.music_manager_data.remote.dto.MusicDto
import com.qmk.music_controller.music_manager_domain.model.Music

fun MusicDto.toMusic(): Music {
    return Music(
        id, filename, fileExtension, title, artist, channelId, uploadDate, isNew, playlistsId
    )
}

fun Music.toMusicDto(): MusicDto {
    return MusicDto(
        id, filename, fileExtension, title, artist, channelId, uploadDate, isNew, playlistsId
    )
}