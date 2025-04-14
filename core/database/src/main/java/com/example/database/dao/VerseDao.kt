package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.VerseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VerseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(verses: List<VerseEntity>)

    @Query(
        value = """
        SELECT *
        FROM verses
        WHERE chapter_id = :chapterId
        ORDER BY verse_id ASC
    """
    )
    fun getVersesForChapter(chapterId: Int): Flow<List<VerseEntity>>
}