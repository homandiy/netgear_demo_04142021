package com.homan.huang.netgeardemo04142021.di.storage

import com.homan.huang.netgeardemo04142021.hellper.Constants.CACHE_DIR
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
object CacheFolderNameModule {

    @Provides
    @Singleton
    @Named("cache_dir")
    fun provideCacheFolderName(): String = CACHE_DIR
}