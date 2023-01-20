package com.qmk.music_controller.music_manager_data.mapper

import com.qmk.music_controller.music_manager_data.remote.dto.NamingRuleDto
import com.qmk.music_controller.music_manager_domain.model.NamingRule

fun NamingRuleDto.toNamingRule(): NamingRule {
    return NamingRule(
        id = id,
        replace = replace,
        replaceBy = replaceBy,
        priority = priority
    )
}

fun NamingRule.toNamingRuleDto(): NamingRuleDto {
    return NamingRuleDto(
        id = id,
        replace = replace,
        replaceBy = replaceBy,
        priority = priority
    )
}