package com.qmk.music_controller.music_manager_data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.qmk.music_controller.music_manager_data.preferences.DefaultMusicManagerPreferences
import com.qmk.music_controller.music_manager_data.remote.MusicManagerApi
import com.qmk.music_controller.music_manager_data.repository.MusicManagerRepositoryImpl
import com.qmk.music_controller.music_manager_domain.preferences.MusicManagerPreferences
import com.qmk.music_controller.music_manager_domain.repository.MusicManagerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicManagerDataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideMusicManagerPreferences(sharedPreferences: SharedPreferences): MusicManagerPreferences {
        return DefaultMusicManagerPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicManagerApi(
        client: OkHttpClient,
        preferences: MusicManagerPreferences
    ): MusicManagerApi {
        val serverInfo = preferences.loadServerInfo()
        val fullUrl =
            if (serverInfo.port == null) serverInfo.url
            else serverInfo.url + ":" + serverInfo.port
        return Retrofit.Builder()
            .baseUrl(fullUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMusicManagerRepository(
        api: MusicManagerApi
    ): MusicManagerRepository {
        return MusicManagerRepositoryImpl(
            api = api
        )
    }
}