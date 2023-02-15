package com.qmk.music_controller.naming_rule_presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.AddNamingRule
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.NamingRuleUseCases
import com.qmk.music_controller.naming_rule_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNamingRuleViewModel @Inject constructor(
    private val useCases: NamingRuleUseCases
): ViewModel() {

    var state by mutableStateOf(AddNamingRuleState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onReplaceEnter(value: String) {
        state = state.copy(replace = value)
    }

    fun onReplaceByEnter(value: String) {
        state = state.copy(replaceBy = value)
    }

    fun onPriorityEnter(value: String) {
        state = state.copy(priority = value)
    }

    fun onReplaceFocusChange(isFocused: Boolean) {
        state = state.copy(
            isReplaceHintVisible = !isFocused && state.replace.isBlank()
        )
    }

    fun onPriorityFocusChange(isFocused: Boolean) {
        state = state.copy(
            isPriorityHintVisible = !isFocused && state.priority.isBlank()
        )
    }

    fun onSaveClick() {
        state = state.copy(processState = ProcessState.PROCESSING)
        viewModelScope.launch {
            val result = useCases.addNamingRule(
                state.replace,
                state.replaceBy,
                state.priority.toInt()
            )
            state = state.copy(
                savedResult = when(result) {
                    is AddNamingRule.Result.Success -> {
                        UiText.StringResource(R.string.naming_rule_added)
                    }
                    is AddNamingRule.Result.Error -> result.message
                },
                processState = when(result) {
                    is AddNamingRule.Result.Success -> ProcessState.NAMING_RULE_SAVED
                    is AddNamingRule.Result.Error -> ProcessState.ERROR_SAVING_NAMING_RULE
                }
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}