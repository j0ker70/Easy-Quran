package com.example.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ChapterWithVerses(
    @Embedded
    val chapter: ChapterEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "chapter_id"
    )
    val verses: List<VerseEntity>
)