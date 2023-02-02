package com.qmk.music_controller.onboarding_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    preferences: MusicManagerPreferences
): ViewModel() {
    var showInitialization by mutableStateOf(true)
        private set

    init {
        val serverInfo = preferences.loadServerInfo()
        showInitialization = (serverInfo.url == "")
    }
}