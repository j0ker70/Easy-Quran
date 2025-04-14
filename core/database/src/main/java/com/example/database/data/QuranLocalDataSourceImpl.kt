package com.example.database.data

import com.example.database.dao.ChapterDao
import com.example.database.dao.VerseDao
import com.example.database.mapper.toDomain
import com.example.database.mapper.toEntity
import com.example.database.model.ChapterEntity
import com.example.database.model.VerseEntity
import com.example.model.Chapter
import com.example.model.Verse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class QuranLocalDataSourceImpl @Inject constructor(
    private val chapterDao: ChapterDao,
    private val verseDao: VerseDao
) : QuranLocalDataSource {

    override fun getAllChapters(): Flow<List<Chapter>> =
        try {
            chapterDao.getAllChapters().map { entityList ->
                entityList.map(ChapterEntity::toDomain)
            }
        } catch (_: IOException) {
            flowOf(emptyList())
        }

    override suspend fun insertChapters(chapters: List<Chapter>) {
        chapterDao.insertChapters(chapters.map(Chapter::toEntity))
    }

    override fun getVersesForChapter(chapterId: Int): Flow<List<Verse>> =
        try {
            verseDao.getVersesForChapter(chapterId).map { entityList ->
                entityList.map(VerseEntity::toDomain)
            }
        } catch (_: IOException) {
            flowOf(emptyList())
        }

    override suspend fun insertVerses(verses: List<Verse>) {
        verseDao.insertVerses(verses.map(Verse::toEntity))
    }
}