package com.qmk.musiccontroller.data.api

import com.qmk.musiccontroller.data.model.NamingRuleInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NamingRulesAPI {
    @GET("/naming-rules")
    suspend fun getNamingRules() : Response<List<NamingRuleInfo>>

    @Headers("Content-Type: application/json")
    @POST("/naming-rules")
    suspend fun postNamingRules(@Body namingRule: NamingRuleInfo): Call<NamingRuleInfo>

    @GET("/naming-rules/{id}")
    suspend fun getNamingRule(@Path("id") id: String) : Response<NamingRuleInfo>

    @Headers("Content-Type: application/json")
    @POST("/naming-rules/{id}")
    suspend fun postNamingRule(@Path("id") id: String, @Body namingRule: NamingRuleInfo)

    @Headers("Content-Type: application/json")
    @DELETE("/naming-rules/{id}")
    suspend fun deleteNamingRule(@Path("id") id: String)
}