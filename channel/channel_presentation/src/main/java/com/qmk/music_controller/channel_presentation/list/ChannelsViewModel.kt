package com.qmk.music_controller.channel_presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.music_manager_domain.use_case.channel.ChannelUseCases
import com.qmk.music_controller.music_manager_domain.use_case.channel.GetChannels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel @Inject constructor(
    private val useCases: ChannelUseCases
): ViewModel() {
    var state by mutableStateOf(ChannelsState())
        private set

    init {
        loadChannels()
    }

    fun onReloadClick() {
        loadChannels()
    }

    private fun loadChannels() {
        state = state.copy(loadingState = LoadingState.LOADING)
        viewModelScope.launch {
            state = when(val channelsResult = useCases.getChannels()) {
                is GetChannels.Result.Success -> {
                    state.copy(
                        channels = channelsResult.channels,
                        loadingState = LoadingState.STANDBY
                    )
                }
                is GetChannels.Result.Error -> {
                    state.copy(loadingState = LoadingState.ERROR)
                }
            }
        }
    }
}