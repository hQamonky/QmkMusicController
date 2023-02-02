package com.qmk.music_controller.music_manager_domain.use_case.channel

data class ChannelUseCases(
    val getChannel: GetChannel,
    val getChannels: GetChannels,
    val updateChannel: UpdateChannel
)