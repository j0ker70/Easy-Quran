package com.example.easyquran.data

import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val dataSource: QuranRemoteDataSource
) : QuranRepository {

    override suspend fun getChapters() = dataSource.getChapters()
}