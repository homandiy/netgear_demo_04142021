package com.homan.huang.netgeardemo04142021.di.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    // switch to show http log
    val HTTP_DEBUG = true
    // http timeout in second
    val mTimeout = 100L

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(Api_Key_Interceptor: Interceptor) =
        if (HTTP_DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(Api_Key_Interceptor)
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .build()
        } else // debug OFF
            OkHttpClient.Builder()
                .addInterceptor(Api_Key_Interceptor)
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .build()
}