package com.qmk.music_controller.music_presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.Music
import com.qmk.music_controller.music_manager_domain.use_case.music.GetNewMusic
import com.qmk.music_controller.music_manager_domain.use_case.music.MusicUseCases
import com.qmk.music_controller.music_manager_domain.use_case.music.UpdateMusic
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.NamingRuleUseCases
import com.qmk.music_controller.music_presentation.R
import com.qmk.music_controller.naming_rule_presentation.add.AddNamingRuleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val useCases: MusicUseCases,
    namingRuleUseCases: NamingRuleUseCases
): ViewModel() {
    var state by mutableStateOf(MusicState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val namingRuleViewModel = AddNamingRuleViewModel(
        useCases = namingRuleUseCases
    )

    init {
        loadNewMusic()
    }

    fun onReloadClick() {
        loadNewMusic()
    }

    fun onEditClick() {
        state = state.copy(
            currentMusic = state.newMusic.first(),
            currentIndex = 0,
            processState = ProcessState.EDITING
        )
    }

    fun onTitleValueChange(title: String) {
        state = state.copy(
            currentMusic = state.currentMusic?.copy(
                title = title
            )
        )
    }

    fun onArtistValueChange(artist: String) {
        state = state.copy(
            currentMusic = state.currentMusic?.copy(
                artist = artist
            )
        )
    }

    fun onSaveValidationClick() {
        applyMusicChanges()
    }

    fun onCancelValidationClick() {
        loadNewMusic()
    }

    fun onAddRuleClick() {
        state = state.copy(processState = ProcessState.ADDING_RULE)
    }

    fun onFinishAddingRule() {
        namingRuleViewModel.resetState()
        state = state.copy(processState = ProcessState.EDITING)
    }

    fun onPreviousMusicClick() {
        if (state.currentIndex == 0) {
            return
        }
        val newMusic = saveCurrentMusic()
        val newCurrentIndex = state.currentIndex - 1
        state = state.copy(
            newMusic = newMusic,
            currentMusic = newMusic[newCurrentIndex],
            currentIndex = newCurrentIndex,
            processState = ProcessState.EDITING,
            customMessage = null
        )
    }

    fun onNextMusicClick() {
        if (state.currentIndex == state.newMusic.lastIndex) {
            applyMusicChanges()
            viewModelScope.launch {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.no_more_new_music
                        )
                    )
                )
            }
            return
        }
        val newMusic = saveCurrentMusic()
        val newCurrentIndex = state.currentIndex + 1
        state = state.copy(
            newMusic = newMusic,
            currentMusic = newMusic[newCurrentIndex],
            currentIndex = newCurrentIndex,
            processState = ProcessState.EDITING,
            customMessage = null
        )
    }

    private fun loadNewMusic() {
        state = state.copy(processState = ProcessState.LOADING)
        viewModelScope.launch {
            state = when(val result = useCases.getNewMusic()) {
                is GetNewMusic.Result.Success -> {
                    MusicState().copy(newMusic = result.newMusic)
                }
                is GetNewMusic.Result.Error -> {
                    MusicState().copy(
                        processState = ProcessState.ERROR,
                        customMessage = result.message
                    )
                }
            }
        }
    }

    private fun saveCurrentMusic(): List<Music> {
        val newMusic = state.newMusic.toMutableList()
        state.currentMusic?.let { newMusic[state.currentIndex] = it.copy(isNew = false) }
        return newMusic
    }

    private fun applyMusicChanges() {
        val newMusic = saveCurrentMusic()
        state = state.copy(
            newMusic = newMusic,
            currentMusic = null,
            currentIndex = 0,
            processState = ProcessState.LOADING,
            customMessage = null
        )
        applyNextMusic(0)
    }

    private fun applyNextMusic(index: Int) {
        viewModelScope.launch {
            when(val result = useCases.updateMusic(state.newMusic[index])) {
                is UpdateMusic.Result.Success -> {
                    if (index != state.newMusic.lastIndex)
                        applyNextMusic(index + 1)
                    else {
                        loadNewMusic()
                        viewModelScope.launch {
                            _uiEvent.send(
                                UiEvent.ShowSnackBar(
                                    UiText.StringResource(
                                        R.string.music_saved
                                    )
                                )
                            )
                        }
                    }
                }
                is UpdateMusic.Result.Error -> {
                    state =  state.copy(
                        processState = ProcessState.ERROR,
                        customMessage = result.message
                    )
                }
            }
        }
    }
}