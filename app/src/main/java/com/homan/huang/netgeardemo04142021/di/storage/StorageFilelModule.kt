package com.homan.huang.netgeardemo04142021.di.storage

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Provide file name for SharedPreferences Storage
 */
@Module
@InstallIn(SingletonComponent::class)
object StorageFilelModule {

    @Provides
    @Singleton
    @Named("storage")
    fun provideFileName(): String = "storage_04092021"
}