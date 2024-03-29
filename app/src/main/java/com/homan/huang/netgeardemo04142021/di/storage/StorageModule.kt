package com.homan.huang.netgeardemo04142021.di.storage


import com.homan.huang.netgeardemo04142021.data.local.storage.SharedPreferencesStorage
import com.homan.huang.netgeardemo04142021.data.local.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(
        storage: SharedPreferencesStorage
    ): Storage

}
