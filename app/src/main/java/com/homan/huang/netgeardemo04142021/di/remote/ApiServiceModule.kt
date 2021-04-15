package com.homan.huang.netgeardemo04142021.di.remote

import com.google.gson.Gson
import com.homan.huang.netgeardemo04142021.data.remote.service.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun providePixabayApi(
        okHttpClient: OkHttpClient,
        gson: Gson,
        baseUrl: String
    ): ImageApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
            .create(ImageApiService::class.java)
    }
}
