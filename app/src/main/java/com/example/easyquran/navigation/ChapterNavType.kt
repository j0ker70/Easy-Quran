package com.example.easyquran.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.easyquran.ui.chapters.ChapterUI
import kotlinx.serialization.json.Json

object ChapterNavType {

    val chapterType = object : NavType<ChapterUI>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): ChapterUI? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ChapterUI {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: ChapterUI): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: ChapterUI) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}