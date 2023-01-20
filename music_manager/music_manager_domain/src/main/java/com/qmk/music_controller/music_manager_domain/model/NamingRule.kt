package com.qmk.music_controller.music_manager_domain.model

data class NamingRule(
    val id: String,
    val replace: String,
    val replaceBy: String,
    val priority: Int
)
