package com.qmk.music_controller.music_manager_domain.use_case.channel

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Channel
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetChannel(
    private val repository: MusicManagerRepository
)  {
    suspend operator fun invoke(
        id: String
    ): Result {
        val playlistResponse = repository.getChannel(id)
        return if (playlistResponse.isSuccess) {
            playlistResponse.getOrNull()?.let {
                Result.Success(it)
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        } else {
            playlistResponse.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(
            val channel: Channel
        ): Result()
        data class Error(val message: UiText): Result()
    }
}