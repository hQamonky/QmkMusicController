package com.qmk.music_controller.setting_presentation.edit_server_settings

data class EditServerSettingsState(
    val downloadOccurrence: Int? = null,
    val isDlOccurrenceHintVisible: Boolean = false,
    val musicDirectory: String = "",
    val isMusicDirHintVisible: Boolean = false,
    val autoDownload: Boolean = false,
    val processState: ProcessState = ProcessState.STANDBY
)

enum class ProcessState {
    STANDBY,
    PROCESSING
}