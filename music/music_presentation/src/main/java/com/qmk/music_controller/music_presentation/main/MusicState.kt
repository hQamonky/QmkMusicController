package com.qmk.music_controller.music_presentation.main

import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Music

data class MusicState(
    val newMusic: List<Music> = emptyList(),
    val currentMusic: Music? = null,
    val currentIndex: Int = 0,
    val processState: ProcessState = ProcessState.STANDBY,
    val customMessage: UiText? = null
)

enum class ProcessState {
    STANDBY,
    EDITING,
    ADDING_RULE,
    LOADING,
    ERROR
}