package com.example.easyquran.ui.verses

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.easyquran.model.domain.Verses
import com.example.easyquran.ui.chapters.ChapterUI
import com.example.easyquran.ui.chapters.toChapter
import com.example.easyquran.ui.components.CircularProgress
import com.example.easyquran.ui.theme.EasyQuranTheme

@Composable
fun VerseListScreen(
    chapterUI: ChapterUI,
    viewModel: VerseListViewModel = hiltViewModel()
) {
    val verseListState by viewModel.verseListState.collectAsStateWithLifecycle()

    VerseListContent(
        verseListState = verseListState,
        onLoadVerses = {
            viewModel.loadVerseForChapter(chapterUI.toChapter())
        }
    )
}

@Composable
fun VerseListContent(
    verseListState: VerseListUIState,
    onLoadVerses: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (verseListState) {
        VerseListUIState.Idle -> onLoadVerses()

        VerseListUIState.Loading -> CircularProgress(modifier = modifier.fillMaxSize())

        is VerseListUIState.Success -> VerseList(
            versesUI = verseListState.verses,
            modifier = modifier
        )

        is VerseListUIState.Failure -> TODO()
    }
}

@Composable
fun VerseList(
    versesUI: VersesUI,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = versesUI.chapterName
        )
        Text(
            text = versesUI.chapterTranslatedName
        )
        Image(
            painter = painterResource(id = versesUI.chapterRevelationPlaceIcon),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(versesUI.verseList) { verse ->
                Text(
                    text = verse
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun VerseListScaffoldPreview() {
    EasyQuranTheme {
        VerseList(previewVersesUI)
    }
}

internal val previewVersesUI = Verses(
    chapterId = 1,
    chapterName = "Al-Fatihah",
    chapterTranslatedName = "The Opener",
    chapterRevelationPlace = "makkah",
    verseList = listOf(
        "With the name of Allah, the All-Merciful, the Very-Merciful.",
        "Praise belongs to Allah, the Lord of all the worlds.",
        "the All-Merciful, the Very Merciful.",
        "the Master of the Day of Requital.",
        "You alone do we worship, and from You alone do we seek help.",
        "Take us on the straight path.",
        "the path of those on whom You have bestowed Your Grace, not of those who have incurred Your wrath, nor of those who have gone astray."
    )
).toVersesUI()