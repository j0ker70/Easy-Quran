package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Query(
        value = """
        SELECT *
        FROM chapters
        ORDER BY id ASC
    """
    )
    fun getAllChapters(): Flow<List<ChapterEntity>>
}