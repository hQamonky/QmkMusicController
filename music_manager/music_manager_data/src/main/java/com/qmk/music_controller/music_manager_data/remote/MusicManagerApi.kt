package com.qmk.music_controller.music_manager_data.remote

import com.qmk.music_controller.music_manager_data.remote.dto.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MusicManagerApi {
    @POST("youtube-dl/update")
    suspend fun updateYoutubeDl(): Response<String>

    @POST("factory-reset")
    suspend fun factoryReset(): Response<Unit>


    // Channels
    @GET("/uploaders")
    suspend fun getChannels(): Response<List<ChannelDto>>

    @GET("/uploaders/{id}")
    suspend fun getChannel(@Path("id") id: String): Response<ChannelDto>

    @Headers("Content-Type: application/json")
    @POST("/uploaders/{id}")
    suspend fun postChannel(
        @Path("id") id: String,
        @Body namingFormat: NamingFormatDto
    ): Response<Unit>


    // Music
    @GET("/music/new")
    suspend fun getNewMusic(): Response<List<MusicDto>>

    @Headers("Content-Type: application/json")
    @POST("/music/{id}")
    suspend fun postMusic(@Path("id") id: String, @Body music: MusicDto): Response<Unit>


    // Naming Rules
    @GET("/naming-rules")
    suspend fun getNamingRules(): Response<List<NamingRuleDto>>

    @Headers("Content-Type: application/json")
    @POST("/naming-rules")
    suspend fun postNamingRules(@Body namingRule: NamingRuleDto): Response<Unit>

    @GET("/naming-rules/{id}")
    suspend fun getNamingRule(@Path("id") id: String): Response<NamingRuleDto>

    @Headers("Content-Type: application/json")
    @POST("/naming-rules/{id}")
    suspend fun postNamingRule(
        @Path("id") id: String,
        @Body namingRule: NamingRuleDto
    ): Response<Unit>

    @Headers("Content-Type: application/json")
    @DELETE("/naming-rules/{id}")
    suspend fun deleteNamingRule(@Path("id") id: String): Response<String>


    // Playlists
    @GET("/playlists")
    suspend fun getPlaylists(): Response<List<PlaylistDto>>

    @Headers("Content-Type: application/json")
    @POST("/playlists")
    suspend fun postPlaylists(@Body playlist: PlaylistAddDto): Response<PlaylistDto?>

    @POST("/playlists/download")
    suspend fun downloadPlaylists(): Response<List<PlaylistDownloadDto>>

    @POST("/playlists/archive-music")
    suspend fun archiveMusic(): Response<List<String>>

    @GET("/playlists/{id}")
    suspend fun getPlaylist(@Path("id") id: String): Response<PlaylistDto>

    @Headers("Content-Type: application/json")
    @POST("/playlists/{id}")
    suspend fun postPlaylist(@Path("id") id: String, @Body playlist: PlaylistDto): Response<Unit>

    @Headers("Content-Type: application/json")
    @DELETE("/playlists/{id}")
    suspend fun deletePlaylist(@Path("id") id: String): Response<String>

    @POST("/playlists/{id}/download")
    suspend fun downloadPlaylist(@Path("id") id: String): Response<PlaylistDownloadDto>


    // Settings
    @GET("/settings")
    suspend fun getSettings(): Response<ServerSettingsDto>

    @Headers("Content-Type: application/json")
    @POST("/settings")
    suspend fun postSettings(@Body settings: ServerSettingsDto): Response<Unit>
}