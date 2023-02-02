package com.qmk.music_controller.music_manager_domain.use_case.naming_rule

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.NamingRule
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetNamingRules(
    private val repository: MusicManagerRepository
)  {
    suspend operator fun invoke(): Result {
        val namingRules = repository.getNamingRules()
        return if (namingRules.isSuccess) {
            Result.Success(namingRules.getOrNull() ?: emptyList())
        } else {
            namingRules.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(
            val namingRules: List<NamingRule>
        ): Result()
        data class Error(val message: UiText): Result()
    }
}