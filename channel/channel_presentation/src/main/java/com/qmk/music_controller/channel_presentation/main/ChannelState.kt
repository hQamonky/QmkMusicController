package com.qmk.music_controller.channel_presentation.main

import com.qmk.music_controller.channel_presentation.edit.EditChannelState
import com.qmk.music_controller.channel_presentation.list.LoadingState
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Channel
import com.qmk.music_controller.music_manager_domain.model.NamingRule

data class ChannelState(
    val processingChannel: Channel? = null,
    val loadingState: LoadingState = LoadingState.STANDBY,
    val customMessage: UiText? = null,
    val editChannelState: EditChannelState = EditChannelState()
)
