package com.qmk.music_controller.core_presentation

import com.qmk.music_controller.core_domain.util.UiText

sealed class UiEvent {
    object Success: UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
}