package com.qmk.music_controller.music_manager_domain.di

import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository
import com.qmk.music_controller.music_manager_domain.use_case.MusicManagerUseCases
import com.qmk.music_controller.music_manager_domain.use_case.channel.*
import com.qmk.music_controller.music_manager_domain.use_case.music.*
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.*
import com.qmk.music_controller.music_manager_domain.use_case.playlist.*
import com.qmk.music_controller.music_manager_domain.use_case.settings.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicManagerDomainModule {

    @Singleton
    @Provides
    fun provideSettingsUseCases(
        repository: MusicManagerRepository,
        preferences: MusicManagerPreferences
    ): SettingsUseCases{
        return SettingsUseCases(
            testConnection = TestConnection(repository),
            getServerInfo = GetServerInfo(preferences),
            setServerInfo = SetServerInfo(preferences),
            factoryReset = FactoryReset(repository),
            updateYoutubeDl = UpdateYoutubeDl(repository),
            getServerSettings = GetServerSettings(repository),
            setServerSettings = SetServerSettings(repository)
        )
    }

    @Singleton
    @Provides
    fun providePlaylistUseCases(
        repository: MusicManagerRepository
    ): PlaylistUseCases{
        return PlaylistUseCases(
            getPlaylists = GetPlaylists(repository),
            addPlaylist = AddPlaylist(repository),
            getPlaylist = GetPlaylist(repository),
            updatePlaylist = UpdatePlaylist(repository),
            deletePlaylist = DeletePlaylist(repository),
            downloadPlaylists = DownloadPlaylists(repository),
            downloadPlaylist = DownloadPlaylist(repository),
            archiveMusic = ArchiveMusic(repository)
        )
    }

    @Singleton
    @Provides
    fun provideMusicUseCases(
        repository: MusicManagerRepository
    ): MusicUseCases{
        return MusicUseCases(
            getNewMusic = GetNewMusic(repository),
            updateMusic = UpdateMusic(repository)
        )
    }

    @Singleton
    @Provides
    fun provideChannelUseCases(
        repository: MusicManagerRepository
    ): ChannelUseCases{
        return ChannelUseCases(
            getChannels = GetChannels(repository),
            updateChannel = UpdateChannel(repository),
            getChannel = GetChannel(repository)
        )
    }

    @Singleton
    @Provides
    fun provideNamingRuleUseCases(
        repository: MusicManagerRepository
    ): NamingRuleUseCases{
        return NamingRuleUseCases(
            addNamingRule = AddNamingRule(repository),
            getNamingRules = GetNamingRules(repository),
            updateNamingRule = UpdateNamingRule(repository),
            getNamingRule = GetNamingRule(repository),
            deleteNamingRule = DeleteNamingRule(repository)
        )
    }

    @Singleton
    @Provides
    fun provideMusicManagerUseCases(
        playlistUseCases: PlaylistUseCases,
        musicUseCases: MusicUseCases,
        channelUseCases: ChannelUseCases,
        namingRuleUseCases: NamingRuleUseCases,
        settingsUseCases: SettingsUseCases
    ): MusicManagerUseCases{
        return MusicManagerUseCases(
            playlistUseCases = playlistUseCases,
            musicUseCases = musicUseCases,
            channelUseCases = channelUseCases,
            namingRuleUseCases = namingRuleUseCases,
            settingsUseCases = settingsUseCases
        )
    }
}