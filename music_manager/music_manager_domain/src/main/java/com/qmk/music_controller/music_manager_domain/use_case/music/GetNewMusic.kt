package com.qmk.music_controller.music_manager_domain.use_case.music

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Music
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetNewMusic(
    private val repository: MusicManagerRepository
)  {
    suspend operator fun invoke(): Result {
        val newMusic = repository.getNewMusic()
        return if (newMusic.isSuccess) {
            Result.Success(newMusic.getOrNull() ?: emptyList())
        } else {
            newMusic.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(
            val newMusic: List<Music>
        ): Result()
        data class Error(val message: UiText): Result()
    }
}