package com.qmk.musiccontroller.domain.model

import com.qmk.musiccontroller.data.model.NamingRuleInfo

data class NamingRuleModel(
    val id: String?,
    val replace: String,
    val replaceBy: String,
    val priority: Int
) {
    constructor(namingRuleInfo: NamingRuleInfo): this(
        namingRuleInfo.id,
        namingRuleInfo.replace,
        namingRuleInfo.replaceBy,
        namingRuleInfo.priority
    )
}
