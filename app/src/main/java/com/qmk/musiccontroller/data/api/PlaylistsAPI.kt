package com.qmk.musiccontroller.data.api

import com.qmk.musiccontroller.data.model.PlaylistDownloadResult
import com.qmk.musiccontroller.data.model.PlaylistEntry
import com.qmk.musiccontroller.data.model.PlaylistInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PlaylistsAPI {
    @GET("/playlists")
    suspend fun getPlaylists() : Response<List<PlaylistInfo>>

    @Headers("Content-Type: application/json")
    @POST("/playlists")
    suspend fun postPlaylists(@Body playlist: PlaylistEntry): Call<PlaylistInfo?>

    @GET("/playlists/download")
    suspend fun downloadPlaylists() : Response<List<PlaylistDownloadResult>>

    @GET("/playlists/archive-music")
    suspend fun archiveMusic() : Response<List<String>>

    @GET("/playlists/{id}")
    suspend fun getPlaylist(@Path("id") id: String) : Response<PlaylistInfo>

    @Headers("Content-Type: application/json")
    @POST("/playlists/{id}")
    suspend fun postPlaylist(@Path("id") id: String, @Body playlist: PlaylistInfo)

    @Headers("Content-Type: application/json")
    @DELETE("/playlists/{id}")
    suspend fun deletePlaylist(@Path("id") id: String)

    @GET("/playlists/{id}/download")
    suspend fun downloadPlaylist(@Path("id") id: String) : Response<PlaylistDownloadResult>
}