package com.qmk.musiccontroller.domain.controller

import android.util.Log
import com.qmk.musiccontroller.data.RetrofitHelper
import com.qmk.musiccontroller.data.api.PlaylistsAPI
import com.qmk.musiccontroller.data.model.PlaylistEntry
import com.qmk.musiccontroller.domain.model.PlaylistModel

class PlaylistController {
    private val playlistsAPI = RetrofitHelper.getInstance().create(PlaylistsAPI::class.java)

    suspend fun getPlaylists(): List<PlaylistModel> {
        val playlists = playlistsAPI.getPlaylists().body()?.map {
            PlaylistModel(it.id, it.name, it.musicIds)
        }
        Log.d("Playlists: ", playlists.toString())
        return playlists ?: emptyList()
    }

    suspend fun addPlaylist(name: String, url: String) {
        playlistsAPI.postPlaylists(PlaylistEntry(name, url))
    }
}