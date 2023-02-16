package com.qmk.music_controller.channel_presentation.list

import com.qmk.music_controller.music_manager_domain.model.Channel

data class ChannelsState(
    val channels: List<Channel> = emptyList(),
    val loadingState: LoadingState = LoadingState.STANDBY
)

enum class LoadingState(val message: String?) {
    STANDBY(null),
    LOADING(null),
    ERROR(null)
}
