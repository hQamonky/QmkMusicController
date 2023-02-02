package com.qmk.music_controller.music_manager_domain.use_case.naming_rule

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class AddNamingRule(
    private val repository: MusicManagerRepository
) {
    suspend operator fun invoke(
        replace: String,
        replaceBy: String,
        priority: Int
    ): Result {
        val queryResult = repository.addNamingRule(replace, replaceBy, priority)
        return if (queryResult.isSuccess) {
            Result.Success
        } else {
            queryResult.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        object Success: Result()
        data class Error(val message: UiText): Result()
    }
}