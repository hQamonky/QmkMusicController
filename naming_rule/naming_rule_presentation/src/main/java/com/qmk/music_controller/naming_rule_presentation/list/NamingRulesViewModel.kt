package com.qmk.music_controller.naming_rule_presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.GetNamingRules
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.NamingRuleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NamingRulesViewModel @Inject constructor(
    private val useCases: NamingRuleUseCases
): ViewModel() {
    var state by mutableStateOf(NamingRulesState())
        private set

    init {
        loadNamingRules()
    }

    fun onReloadClick() {
        loadNamingRules()
    }

    private fun loadNamingRules() {
        state = state.copy(loadingState = LoadingState.LOADING)
        viewModelScope.launch {
            state = when(val namingRulesResult = useCases.getNamingRules()) {
                is GetNamingRules.Result.Success -> {
                    state.copy(
                        namingRules = namingRulesResult.namingRules,
                        loadingState = LoadingState.STANDBY
                    )
                }
                is GetNamingRules.Result.Error -> {
                    state.copy(loadingState = LoadingState.ERROR)
                }
            }
        }
    }
}