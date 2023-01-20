package com.qmk.music_controller.music_manager_data.mapper

import com.qmk.music_controller.music_manager_data.remote.dto.ChannelDto
import com.qmk.music_controller.music_manager_data.remote.dto.NamingFormatDto
import com.qmk.music_controller.music_manager_domain.model.Channel
import com.qmk.music_controller.music_manager_domain.model.NamingFormat

fun ChannelDto.toChannel(): Channel {
    return Channel(
        id = id,
        name = name,
        namingFormat = namingFormat.toNamingFormat()
    )
}

fun NamingFormatDto.toNamingFormat(): NamingFormat {
    return NamingFormat(
        separator = separator,
        aristIsBeforeTitle = aristIsBeforeTitle
    )
}

fun NamingFormat.toNamingFormatDto(): NamingFormatDto {
    return NamingFormatDto(
        separator = separator,
        aristIsBeforeTitle = aristIsBeforeTitle
    )
}