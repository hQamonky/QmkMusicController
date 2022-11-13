package com.qmk.musiccontroller.data.api

import com.qmk.musiccontroller.data.model.MusicInfo
import retrofit2.Response
import retrofit2.http.*

interface MusicAPI {
    @GET("/music/new")
    suspend fun getNewMusic() : Response<List<MusicInfo>>

    @Headers("Content-Type: application/json")
    @POST("/music/{id}")
    suspend fun postMusic(@Path("id") id: String, @Body music: MusicInfo)
}