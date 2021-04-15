package com.homan.huang.netgeardemo04142021.di.remote

import com.homan.huang.netgeardemo04142021.BuildConfig
import com.homan.huang.netgeardemo04142021.hellper.Constants
import com.homan.huang.netgeardemo04142021.hellper.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    fun provideInterceptor(
        @Named("api_key") apiKey: String
    ): Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header(Constants.CONTENT_HEADER, "application/json")
        requestBuilder.header(Constants.API_KEY_HEADER, apiKey)
        chain.proceed(requestBuilder.build())
    }
}