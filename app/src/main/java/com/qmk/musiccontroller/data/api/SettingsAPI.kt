package com.qmk.musiccontroller.data.api

import com.qmk.musiccontroller.data.model.SettingsInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SettingsAPI {
    @GET("/settings")
    suspend fun getSettings() : Response<SettingsInfo>

    @Headers("Content-Type: application/json")
    @POST("/settings")
    suspend fun postSettings(@Body settings: SettingsInfo)

    @GET("/factory-reset")
    suspend fun factoryReset() : Response<String>

    @GET("/youtube-dl/update")
    suspend fun updateYoutubeDl() : Response<String>
}