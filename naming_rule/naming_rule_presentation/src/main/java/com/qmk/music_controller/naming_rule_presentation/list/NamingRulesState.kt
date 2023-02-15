package com.qmk.music_controller.naming_rule_presentation.list

import com.qmk.music_controller.music_manager_domain.model.NamingRule

data class NamingRulesState(
    val namingRules: List<NamingRule> = emptyList(),
    val loadingState: LoadingState = LoadingState.STANDBY
)

enum class LoadingState(val message: String?) {
    STANDBY(null),
    LOADING(null),
    ERROR(null)
}
