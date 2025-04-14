package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.QuranDatabase
import com.example.database.dao.ChapterDao
import com.example.database.dao.VerseDao
import com.example.database.data.QuranLocalDataSource
import com.example.database.data.QuranLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): QuranDatabase = Room.databaseBuilder(
        context,
        QuranDatabase::class.java,
        "quran-database"
    ).build()

    @Provides
    @Singleton
    fun providesLocalDatasource(
        chapterDao: ChapterDao,
        verseDao: VerseDao
    ): QuranLocalDataSource = QuranLocalDataSourceImpl(chapterDao, verseDao)
}