package com.example.easyquran.ui.chapters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.easyquran.domain.Chapter
import com.example.easyquran.ui.chapters.models.ChapterUI
import com.example.easyquran.ui.chapters.models.toChapterUI
import com.example.easyquran.ui.theme.EasyQuranTheme

@Composable
fun SingleChapter(
    chapter: ChapterUI,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chapter.id,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp)
        ) {
            Text(
                text = chapter.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Text(
                    text = chapter.translatedName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                VerticalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = chapter.versesCount,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Icon(
            painter = painterResource(id = chapter.revelationPlaceIcon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(30.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun SingleChapterPreview() {
    EasyQuranTheme {
        SingleChapter(
            chapter = previewChapter,
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}

internal val previewChapter = Chapter(
    id = 1,
    name = "Al-Fatihah",
    translatedName = "The Opener",
    versesCount = 7,
    revelationPlace = "makkah"
).toChapterUI()