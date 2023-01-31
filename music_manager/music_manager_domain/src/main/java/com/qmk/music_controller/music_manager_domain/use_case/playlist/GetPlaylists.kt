package com.qmk.music_controller.music_manager_domain.use_case.playlist

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetPlaylists(
    private val repository: MusicManagerRepository
)  {
    suspend operator fun invoke(): Result {
        val playlists = repository.getPlaylists()
        return if (playlists.isSuccess) {
            Result.Success(playlists.getOrNull() ?: emptyList())
        } else {
            playlists.exceptionOrNull()?.message?.let {
                Result.Error(UiText.DynamicString(it))
            } ?:
            Result.Error(UiText.StringResource(R.string.unknown_error))
        }
    }

    sealed class Result {
        data class Success(
            val playlists: List<Playlist>
        ): Result()
        data class Error(val message: UiText): Result()
    }
}