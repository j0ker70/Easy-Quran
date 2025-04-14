package com.example.database.mapper

import com.example.database.model.VerseEntity
import com.example.model.Verse

fun VerseEntity.toDomain() = Verse(
    chapterId = chapterId,
    verseId = verseId,
    arabic = arabic,
    translated = translated
)

fun Verse.toEntity() = VerseEntity(
    chapterId = chapterId,
    verseId = verseId,
    arabic = arabic,
    translated = translated
)