package com.example.easyquran.data

import com.example.easyquran.domain.Chapter
import com.example.easyquran.ui.chapters.models.toChapterUI

data class ChaptersListDto(
    val chapters: List<ChapterDto>
)

fun ChaptersListDto.toChapters() = chapters.map { it.toChapter().toChapterUI() }

data class ChapterDto(
    val bismillah_pre: Boolean,
    val id: Int,
    val name_arabic: String,
    val name_complex: String,
    val name_simple: String,
    val pages: List<Int>,
    val revelation_order: Int,
    val revelation_place: String,
    val translated_name: TranslatedNameDto,
    val verses_count: Int
)

fun ChapterDto.toChapter() = Chapter(
    id = id,
    name = name_simple,
    translatedName = translated_name.name,
    versesCount = verses_count,
    revelationPlace = revelation_place
)

data class TranslatedNameDto(
    val language_name: String,
    val name: String
)

