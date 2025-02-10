package com.example.easyquran.utils

import androidx.annotation.DrawableRes
import com.example.easyquran.R

fun revelationPlaceIconToString(@DrawableRes icon: Int): String {
    return if (icon == R.drawable.makkah) "makkah" else "madinah"
}

@DrawableRes
fun revelationPlaceStringToIcon(place: String): Int {
    return if (place == "makkah") R.drawable.makkah else R.drawable.madinah
}