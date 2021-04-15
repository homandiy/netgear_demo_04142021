package com.homan.huang.netgeardemo04142021.di

import com.homan.huang.netgeardemo04142021.data.local.ImageManifestDatabase
import com.homan.huang.netgeardemo04142021.data.remote.service.ImageApiService
import com.homan.huang.netgeardemo04142021.repository.BaseRepository
import com.homan.huang.netgeardemo04142021.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

/**
 * Inject Repository
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(
        imageCachedFolder: File,
        imageApiService: ImageApiService,
        imageDb: ImageManifestDatabase
    ): BaseRepository = ImageRepository(imageCachedFolder, imageApiService, imageDb)
}