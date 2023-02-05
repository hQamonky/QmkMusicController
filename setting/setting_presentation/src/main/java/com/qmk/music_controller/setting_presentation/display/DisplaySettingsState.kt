package com.qmk.music_controller.setting_presentation.display

import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.R

data class DisplaySettingsState(
    val ipAddress: UiText = UiText.StringResource(R.string.unknown),
    val portNumber: UiText = UiText.StringResource(R.string.unknown),
    val musicDirectory: UiText = UiText.StringResource(R.string.unknown),
    val downloadOccurrence: UiText = UiText.StringResource(R.string.unknown),
    val autoDownload: UiText = UiText.StringResource(R.string.unknown)
)