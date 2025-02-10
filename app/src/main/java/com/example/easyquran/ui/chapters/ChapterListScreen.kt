package com.example.easyquran.ui.chapters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.ui.components.CircularProgress
import com.example.easyquran.ui.theme.EasyQuranTheme

@Composable
fun ChapterListScreen(
    onChapterClick: (ChapterUI) -> Unit,
    viewModel: ChapterListViewModel = hiltViewModel()
) {
    val chapterListUIState by viewModel.chaptersListState.collectAsStateWithLifecycle()

    ChapterListContent(
        chapterListUIState = chapterListUIState,
        onLoadChapters = { viewModel.loadChapters() },
        onChapterClick = onChapterClick
    )
}

@Composable
private fun ChapterListContent(
    chapterListUIState: ChapterListUIState,
    onLoadChapters: () -> Unit,
    onChapterClick: (ChapterUI) -> Unit,
) {
    when (chapterListUIState) {
        ChapterListUIState.Idle -> onLoadChapters()

        ChapterListUIState.Loading -> {
            CircularProgress(modifier = Modifier.fillMaxSize())
        }

        is ChapterListUIState.Success -> {
            ChapterList(
                chapterList = chapterListUIState.chapterList,
                onChapterClick = onChapterClick
            )
        }

        is ChapterListUIState.Failure -> {
            // TODO
        }
    }
}

@Composable
fun ChapterList(
    chapterList: List<ChapterUI>,
    modifier: Modifier = Modifier,
    onChapterClick: (ChapterUI) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(chapterList) { chapter ->
            SingleChapter(
                chapter = chapter,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onChapterClick(chapter) },
            )
            HorizontalDivider()
        }
    }
}

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
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
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
            tint = Color.Unspecified,
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

@PreviewLightDark
@Composable
private fun ChaptersListPreview() {
    EasyQuranTheme {
        ChapterListContent(
            chapterListUIState = ChapterListUIState.Success(
                (1..100).map {
                    previewChapter.copy(id = it.toString())
                }
            ),
            onLoadChapters = {},
            onChapterClick = {}
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