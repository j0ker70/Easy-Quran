package com.example.network.utils

data class ChapterAndVerseId(
    val chapterId: Int,
    val verseId: Int
)

fun getChapterAndVerseId(verseKey: String?): ChapterAndVerseId {
    verseKey?.split(":")?.let { ids ->
        return when {
            ids.size != 2 -> ChapterAndVerseId(0, 0)
            else -> ChapterAndVerseId(ids[0].toInt(), ids[1].toInt())
        }
    }
    return ChapterAndVerseId(0, 0)
}