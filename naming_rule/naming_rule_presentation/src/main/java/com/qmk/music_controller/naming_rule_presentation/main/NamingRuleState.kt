package com.qmk.music_controller.naming_rule_presentation.main

import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.NamingRule
import com.qmk.music_controller.naming_rule_presentation.edit.EditNamingRuleState
import com.qmk.music_controller.naming_rule_presentation.list.LoadingState

data class NamingRuleState(
    val processingNamingRule: NamingRule? = null,
    val loadingState: LoadingState = LoadingState.STANDBY,
    val customMessage: UiText? = null,
    val editNamingRuleState: EditNamingRuleState = EditNamingRuleState()
)
