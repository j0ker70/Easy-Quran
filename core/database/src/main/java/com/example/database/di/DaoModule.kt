package com.example.database.di

import com.example.database.QuranDatabase
import com.example.database.dao.ChapterDao
import com.example.database.dao.VerseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideChapterDao(
        database: QuranDatabase
    ): ChapterDao = database.chapterDao()

    @Provides
    fun provideVerseDao(
        database: QuranDatabase
    ): VerseDao = database.verseDao()
}