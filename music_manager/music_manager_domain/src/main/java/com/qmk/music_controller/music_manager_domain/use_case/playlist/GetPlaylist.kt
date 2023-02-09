package com.qmk.music_controller.music_manager_domain.use_case.playlist

import com.qmk.music_controller.core_domain.R
import com.qmk.music_controller.core_domain.util.UiText
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository

class GetPlaylist(
    private val repository: MusicManagerRepository
)  {
    suspend operator fun invoke(
        id: String
    ): Result {
        val playlistResponse = repository.getPlaylist(id)
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
            val playlist: Playlist
        ): Result()
        data class Error(val message: UiText): Result()
    }
}