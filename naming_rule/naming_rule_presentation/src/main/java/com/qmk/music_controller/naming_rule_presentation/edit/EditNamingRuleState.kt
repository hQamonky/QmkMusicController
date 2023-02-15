package com.qmk.music_controller.naming_rule_presentation.edit

import com.qmk.music_controller.core_domain.util.UiText

data class EditNamingRuleState(
    val replace: String = "",
    val replaceBy: String = "",
    val priority: String = "",
    val savedResult: UiText? = null
)
