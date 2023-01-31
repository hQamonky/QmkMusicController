package com.qmk.music_controller.music_manager_domain.use_case.settings

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.ServerSettings
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetServerSettings(
    private val repository: MusicManagerRepository
) {
    suspend operator fun invoke(): Result {
        val serverSettings = repository.getSettings()
        return if (serverSettings.isSuccess) {
            Result.Success(serverSettings.getOrNull())
        } else {
            serverSettings.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(
            val serverSettings: ServerSettings?
        ): Result()
        data class Error(val message: UiText): Result()
    }
}