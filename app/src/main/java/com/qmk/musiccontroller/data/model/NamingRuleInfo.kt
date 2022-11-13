package com.qmk.musiccontroller.data.model

data class NamingRuleInfo(
    val id: String? = null,
    val replace: String,
    val replaceBy: String = "",
    val priority: Int = 2
)
