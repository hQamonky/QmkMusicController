package com.qmk.music_controller.naming_rule_presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.music_manager_domain.model.NamingRule
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.DeleteNamingRule
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.GetNamingRule
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.NamingRuleUseCases
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.UpdateNamingRule
import com.qmk.music_controller.naming_rule_presentation.R
import com.qmk.music_controller.naming_rule_presentation.list.LoadingState
import com.qmk.music_controller.naming_rule_presentation.main.NamingRuleRoute.LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NamingRuleViewModel @Inject constructor(
    private val useCases: NamingRuleUseCases
): ViewModel() {
    var state by mutableStateOf(NamingRuleState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun getNamingRule(
        id: String,
        onProcessSuccess: (namingRule: NamingRule) -> Unit
    ) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = UiText.StringResource(
                R.string.loading_get_naming_rule_info)
        )
        when(val result = useCases.getNamingRule(id)) {
            is GetNamingRule.Result.Success -> {
                state = state.copy(
                    loadingState = LoadingState.STANDBY,
                    customMessage = null,
                    processingNamingRule = result.namingRule
                )
                onProcessSuccess(result.namingRule)
            }
            is GetNamingRule.Result.Error -> {
                state = state.copy(
                    loadingState = LoadingState.ERROR,
                    customMessage = result.message,
                    processingNamingRule = null
                )
            }
        }
    }


    // Edit Rule -----------------------------


    fun getNamingRuleForEdit(id: String) {
        viewModelScope.launch {
            getNamingRule(id) {
                state = state.copy(
                    editNamingRuleState = state.editNamingRuleState.copy(
                        replace = it.replace,
                        replaceBy = it.replaceBy,
                        priority = it.priority.toString()
                    )
                )
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(NamingRuleRoute.EDIT_NAMING_RULE))
                }
            }
        }
    }

    fun onEditReplaceEnter(value: String) {
        state = state.copy(
            editNamingRuleState = state.editNamingRuleState.copy(
                replace = value
            )
        )
    }

    fun onEditReplaceByEnter(value: String) {
        state = state.copy(
            editNamingRuleState = state.editNamingRuleState.copy(
                replaceBy = value
            )
        )
    }

    fun onEditPriorityEnter(value: String) {
        state = state.copy(
            editNamingRuleState = state.editNamingRuleState.copy(
                priority = value
            )
        )
    }

    fun editNamingRule(namingRule: NamingRule) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            val newNamingRule = namingRule.copy(
                replace = state.editNamingRuleState.replace,
                replaceBy = state.editNamingRuleState.replaceBy,
                priority = state.editNamingRuleState.priority.toInt(),
            )
            when(val result = useCases.updateNamingRule(newNamingRule)) {
                is UpdateNamingRule.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.naming_rule_updated)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is UpdateNamingRule.Result.Error -> {
                    state = state.copy(
                        loadingState = LoadingState.ERROR,
                        customMessage = result.message
                    )
                }
            }
        }
    }


    // Delete Rule -----------------------------


    fun getNamingRuleForDelete(id: String) {
        viewModelScope.launch {
            getNamingRule(id) {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(NamingRuleRoute.DELETE_NAMING_RULE))
                }
            }
        }
    }

    fun deleteNamingRule(namingRule: NamingRule) {
        state = state.copy(
            loadingState = LoadingState.LOADING,
            customMessage = null
        )
        viewModelScope.launch {
            when(val result = useCases.deleteNamingRule(namingRule)) {
                is DeleteNamingRule.Result.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(LIST))
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(
                        R.string.naming_rule_deleted)))
                    state = state.copy(
                        loadingState = LoadingState.STANDBY,
                        customMessage = null
                    )
                }
                is DeleteNamingRule.Result.Error -> {
                    state = state.copy(
                        loadingState = LoadingState.ERROR,
                        customMessage = result.message
                    )
                }
            }
        }
    }
}