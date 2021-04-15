package com.homan.huang.netgeardemo04142021.di.data

import android.content.Context
import androidx.room.Room
import com.homan.huang.netgeardemo04142021.hellper.Constants.DATABASE_NAME
import com.homan.huang.netgeardemo04142021.data.local.ImageManifestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Inject Database
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideImageManifestDatabase(
        @ApplicationContext context: Context
    ) : ImageManifestDatabase = Room.databaseBuilder(
                                    context,
                                    ImageManifestDatabase::class.java,
                                    DATABASE_NAME
                                ).build()

}