package com.example.easyquran.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.easyquran.R

val NotoSansArabic = FontFamily(
    Font(
        resId = R.font.noto_sans_arabic_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.noto_sans_arabic_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.noto_sans_arabic_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.noto_sans_arabic_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.noto_sans_arabic_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.noto_sans_arabic_black,
        weight = FontWeight.Black
    )
)

val NaskhFont = FontFamily(
    Font(
        resId = R.font.naskh_nastaleeq_indo_pak_qwbw
    )
)

// Set of Material typography styles to start with
val Typography = Typography()