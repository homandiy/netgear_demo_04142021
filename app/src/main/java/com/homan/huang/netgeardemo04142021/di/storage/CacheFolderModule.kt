package com.homan.huang.netgeardemo04142021.di.storage

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object CacheFolderModule {

    @Provides
    @Singleton
    fun provideCacheFolder(
        @Named("cache_dir") folderName: String,
        @ApplicationContext context:Context
    ): File = context.getDir(folderName, Context.MODE_PRIVATE)
}