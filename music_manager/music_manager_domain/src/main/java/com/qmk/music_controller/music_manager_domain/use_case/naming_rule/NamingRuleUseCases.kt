package com.qmk.music_controller.music_manager_domain.use_case.naming_rule

data class NamingRuleUseCases(
    val addNamingRule: AddNamingRule,
    val deleteNamingRule: DeleteNamingRule,
    val getNamingRule: GetNamingRule,
    val getNamingRules: GetNamingRules,
    val updateNamingRule: UpdateNamingRule
)