package com.qmk.music_controller.music_manager_domain.use_case.settings

data class SettingsUseCases(
    val factoryReset: FactoryReset,
    val getServerInfo: GetServerInfo,
    val getServerSettings: GetServerSettings,
    val setServerInfo: SetServerInfo,
    val setServerSettings: SetServerSettings,
    val testConnection: TestConnection,
    val updateYoutubeDl: UpdateYoutubeDl
)
