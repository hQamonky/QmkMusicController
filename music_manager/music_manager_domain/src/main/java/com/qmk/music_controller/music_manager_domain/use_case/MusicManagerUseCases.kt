package com.qmk.music_controller.music_manager_domain.use_case

import com.qmk.music_controller.music_manager_domain.use_case.channel.ChannelUseCases
import com.qmk.music_controller.music_manager_domain.use_case.music.MusicUseCases
import com.qmk.music_controller.music_manager_domain.use_case.naming_rule.NamingRuleUseCases
import com.qmk.music_controller.music_manager_domain.use_case.playlist.PlaylistUseCases
import com.qmk.music_controller.music_manager_domain.use_case.settings.SettingsUseCases

data class MusicManagerUseCases(
    val playlistUseCases: PlaylistUseCases,
    val musicUseCases: MusicUseCases,
    val channelUseCases: ChannelUseCases,
    val namingRuleUseCases: NamingRuleUseCases,
    val settingsUseCases: SettingsUseCases
)