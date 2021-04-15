package com.homan.huang.netgeardemo04142021.di.remote

import com.homan.huang.netgeardemo04142021.BuildConfig
import com.homan.huang.netgeardemo04142021.hellper.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiKeyModule {

    @Provides
    @Singleton
    @Named("api_key")
    fun provideApiKey(): String = BuildConfig.API_KEY
}