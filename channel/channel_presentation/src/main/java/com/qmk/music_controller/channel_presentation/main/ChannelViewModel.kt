package com.qmk.music_controller.channel_presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.channel_presentation.R
import com.qmk.music_controller.channel_presentation.list.LoadingState
import com.qmk.music_controller.channel_presentation.main.ChannelRoute.LIST
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.use_case.channel.ChannelUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import com.qmk.music_controller.music_manager_domain.model.Channel
import com.qmk.music_controller.music_manager_domain.model.NamingFormat
import com.qmk.music_controller.music_manager_domain.use_case.channel.GetChannel
import com.qmk.music_controller.music_manager_domain.use_case.channel.UpdateChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val useCases: ChannelUseCases
): ViewModel() {
    var state by mutableStateOf(ChannelState())
        private set

    private val _uiEvent = kotlinx.coroutines.channels.Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun getChannel(
        id: String,
        onProcessSuccess: (channel: Channel) -> Unit
    ) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = UiText.StringResource(
                R.string.loading_get_channel_info)
        )
        when(val result = useCases.getChannel(id)) {
            is GetChannel.Result.Success -> {
                state = state.copy(
                    loadingState = LoadingState.STANDBY,
                    customMessage = null,
                    processingChannel = result.channel
                )
                onProcessSuccess(result.channel)
            }
            is GetChannel.Result.Error -> {
                state = state.copy(
                    loadingState = LoadingState.ERROR,
                    customMessage = result.message,
                    processingChannel = null
                )
            }
        }
    }


    // Edit Rule -----------------------------


    fun getChannelForEdit(id: String) {
        viewModelScope.launch {
            getChannel(id) {
                state = state.copy(
                    editChannelState = state.editChannelState.copy(
                        separator = it.namingFormat.separator,
                        isArtistBeforeTitle = it.namingFormat.aristIsBeforeTitle
                    )
                )
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(ChannelRoute.EDIT_CHANNEL))
                }
            }
        }
    }

    fun onEditSeparatorEnter(value: String) {
        state = state.copy(
            editChannelState = state.editChannelState.copy(
                separator = value
            )
        )
    }

    fun onArtistBeforeTitleChange(value: Boolean) {
        state = state.copy(
            editChannelState = state.editChannelState.copy(
                isArtistBeforeTitle = value
            )
        )
    }

    fun editChannel(channel: Channel) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            val newChannel = channel.copy(
                namingFormat = NamingFormat(
                    separator = state.editChannelState.separator,
                    aristIsBeforeTitle = state.editChannelState.isArtistBeforeTitle
                )
            )
            when(val result = useCases.updateChannel(newChannel)) {
                is UpdateChannel.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.channel_updated)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is UpdateChannel.Result.Error -> {
                    state = state.copy(
                        loadingState = LoadingState.ERROR,
                        customMessage = result.message
                    )
                }
            }
        }
    }
}