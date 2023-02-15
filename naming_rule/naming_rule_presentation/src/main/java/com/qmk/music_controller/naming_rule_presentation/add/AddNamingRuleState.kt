package com.qmk.music_controller.naming_rule_presentation.add

import com.qmk.music_controller.core_domain.util.UiText

data class AddNamingRuleState(
    val replace: String = "",
    val replaceBy: String = "",
    val priority: String = "",
    val isReplaceHintVisible: Boolean = false,
    val isPriorityHintVisible: Boolean = false,
    val processState: ProcessState = ProcessState.ENTERING_FIELDS,
    val savedResult: UiText? = null
)

enum class ProcessState {
    ENTERING_FIELDS,
    PROCESSING,
    NAMING_RULE_SAVED,
    ERROR_SAVING_NAMING_RULE
}
