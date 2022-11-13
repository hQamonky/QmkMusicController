package com.qmk.musiccontroller.data.api

import com.qmk.musiccontroller.data.model.NamingFormatInfo
import com.qmk.musiccontroller.data.model.UploaderInfo
import retrofit2.Response
import retrofit2.http.*

interface UploadersAPI {
    @GET("/uploaders")
    suspend fun getUploaders() : Response<List<UploaderInfo>>

    @GET("/uploaders/{id}")
    suspend fun getUploader(@Path("id") id: String) : Response<UploaderInfo>

    @Headers("Content-Type: application/json")
    @POST("/uploaders/{id}")
    suspend fun postUploader(@Path("id") id: String, @Body namingFormat: NamingFormatInfo)
}