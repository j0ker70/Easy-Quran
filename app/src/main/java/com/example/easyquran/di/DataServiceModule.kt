package com.example.easyquran.di

import com.example.easyquran.data.QuranRemoteDataSource
import com.example.easyquran.data.QuranRemoteDataSourceImpl
import com.example.easyquran.data.QuranRepository
import com.example.easyquran.data.QuranRepositoryImpl
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