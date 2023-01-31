package com.qmk.music_controller.music_manager_domain.use_case.playlist

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class AddPlaylist(
    private val repository: MusicManagerRepository
) {
    suspend operator fun invoke(
        name: String,
        url: String
    ): Result {
        val queryResult = repository.addPlaylist(name, url)
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