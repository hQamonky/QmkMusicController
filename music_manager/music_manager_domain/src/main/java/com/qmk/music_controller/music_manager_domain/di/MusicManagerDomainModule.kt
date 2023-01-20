package com.qmk.music_controller.music_manager_domain.di

import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository
import com.qmk.music_controller.music_manager_domain.use_case.MusicManagerUseCases
import com.qmk.music_controller.music_manager_domain.use_case.playlist.AddPlaylist
import com.qmk.music_controller.music_manager_domain.use_case.playlist.GetPlaylists
import com.qmk.music_controller.music_manager_domain.use_case.playlist.PlaylistUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object MusicManagerDomainModule {

    @Singleton
    @Provides
    fun provideMusicManagerUseCases(
        repository: MusicManagerRepository,
        preferences: MusicManagerPreferences
    ): MusicManagerUseCases{
        return MusicManagerUseCases(
            playlistUseCases = PlaylistUseCases(
                getPlaylists = GetPlaylists(),
                addPlaylist = AddPlaylist(repository)
            )
        )
    }
}