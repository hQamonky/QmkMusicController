package com.qmk.music_controller.music_manager_domain.use_case.playlist

data class PlaylistUseCases(
    val addPlaylist: AddPlaylist,
    val archiveMusic: ArchiveMusic,
    val deletePlaylist: DeletePlaylist,
    val downloadPlaylist: DownloadPlaylist,
    val downloadPlaylists: DownloadPlaylists,
    val getPlaylist: GetPlaylist,
    val getPlaylists: GetPlaylists,
    val updatePlaylist: UpdatePlaylist
)