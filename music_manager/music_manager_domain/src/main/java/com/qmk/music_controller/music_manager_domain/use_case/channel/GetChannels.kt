package com.qmk.music_controller.music_manager_domain.use_case.channel

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Channel
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetChannels(
    private val repository: MusicManagerRepository
)  {
    suspend operator fun invoke(): Result {
        val channels = repository.getChannels()
        return if (channels.isSuccess) {
            Result.Success(channels.getOrNull() ?: emptyList())
        } else {
            channels.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(
            val namingRules: List<Channel>
        ): Result()
        data class Error(val message: UiText): Result()
    }
}