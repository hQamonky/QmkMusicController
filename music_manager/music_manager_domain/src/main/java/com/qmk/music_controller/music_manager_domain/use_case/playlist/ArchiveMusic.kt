package com.qmk.music_controller.music_manager_domain.use_case.playlist

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class ArchiveMusic(
    private val repository: MusicManagerRepository
) {
    suspend operator fun invoke(): Result {
        val queryResult = repository.archiveMusic()
        return if (queryResult.isSuccess) {
            queryResult.getOrNull()?.let {
                Result.Success(it)
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        } else {
            queryResult.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(val archivedMusic: List<String>): Result()
        data class Error(val message: UiText): Result()
    }
}