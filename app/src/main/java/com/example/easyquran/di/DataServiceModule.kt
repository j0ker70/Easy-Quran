package com.example.easyquran.di

import com.example.easyquran.data.QuranRepository
import com.example.easyquran.data.QuranRepositoryImpl
import com.example.network.data.QuranRemoteDataSource
import com.example.network.data.QuranRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataServiceModule {

    @Binds
    abstract fun bindQuranRemoteDataSource(
        remoteDataSource: QuranRemoteDataSourceImpl
    ): QuranRemoteDataSource

    @Binds
    abstract fun bindQuranRepository(
        quranRepositoryImpl: QuranRepositoryImpl
    ): QuranRepository
}