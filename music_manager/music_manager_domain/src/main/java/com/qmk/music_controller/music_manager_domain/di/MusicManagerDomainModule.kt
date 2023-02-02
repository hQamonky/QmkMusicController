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
            musicUseCases = MusicUseCases(
                getNewMusic = GetNewMusic(repository),
                updateMusic = UpdateMusic(repository)
            ),
            channelUseCases = ChannelUseCases(
                getChannels = GetChannels(repository),
                updateChannel = UpdateChannel(repository),
                getChannel = GetChannel(repository)
            ),
            namingRuleUseCases = NamingRuleUseCases(
                addNamingRule = AddNamingRule(repository),
                getNamingRules = GetNamingRules(repository),
                updateNamingRule = UpdateNamingRule(repository),
                getNamingRule = GetNamingRule(repository),
                deleteNamingRule = DeleteNamingRule(repository)
            ),
            settingsUseCases = SettingsUseCases(
                testConnection = TestConnection(repository),
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