package com.qmk.music_controller.music_manager_domain.di

import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository
import com.qmk.music_controller.music_manager_domain.use_case.MusicManagerUseCases
import com.qmk.music_controller.music_manager_domain.use_case.playlist.*
import com.qmk.music_controller.music_manager_domain.use_case.settings.*
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
                getPlaylists = GetPlaylists(repository),
                addPlaylist = AddPlaylist(repository),
                getPlaylist = GetPlaylist(repository),
                updatePlaylist = UpdatePlaylist(repository),
                deletePlaylist = DeletePlaylist(repository),
                downloadPlaylists = DownloadPlaylists(repository),
                downloadPlaylist = DownloadPlaylist(repository),
                archiveMusic = ArchiveMusic(repository)
            ),
            settingsUseCases = SettingsUseCases(
                getServerInfo = GetServerInfo(preferences),
                setServerInfo = SetServerInfo(preferences),
                factoryReset = FactoryReset(repository),
                updateYoutubeDl = UpdateYoutubeDl(repository),
                getServerSettings = GetServerSettings(repository),
                setServerSettings = SetServerSettings(repository)
            )
        )
    }
}