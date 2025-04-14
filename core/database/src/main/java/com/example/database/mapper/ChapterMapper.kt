package com.example.database.mapper

import com.example.database.model.ChapterEntity
import com.example.model.Chapter

fun ChapterEntity.toDomain() = Chapter(
    id = id,
    name = name,
    translatedName = translatedName,
    versesCount = versesCount,
    revelationPlace = revelationPlace
)

fun Chapter.toEntity() = ChapterEntity(
    id = id,
    name = name,
    translatedName = translatedName,
    versesCount = versesCount,
    revelationPlace = revelationPlace
)