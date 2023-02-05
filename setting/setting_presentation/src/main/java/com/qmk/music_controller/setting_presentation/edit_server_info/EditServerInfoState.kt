package com.qmk.music_controller.setting_presentation.edit_server_info

data class EditServerInfoState(
    val ipAddress: String = "",
    val isIpHintVisible: Boolean = false,
    val portNumber: Int? = null,
    val isPortHintVisible: Boolean = false,
    val processState: ProcessState = ProcessState.STANDBY
)

enum class ProcessState {
    STANDBY,
    PROCESSING
}