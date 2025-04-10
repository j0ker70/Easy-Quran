@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.easyquran.ui.verses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.easyquran.ui.chapters.ChapterUI
import com.example.easyquran.ui.chapters.previewChapter
import com.example.easyquran.ui.chapters.toChapter
import com.example.easyquran.ui.components.CircularProgress
import com.example.easyquran.ui.theme.EasyQuranTheme
import com.example.easyquran.ui.theme.NaskhFont
import com.example.model.ChapterVerse
import com.example.model.Verse

@Composable
fun VerseListScreen(
    navController: NavController,
    chapterUI: ChapterUI,
    viewModel: VerseListViewModel = hiltViewModel()
) {
    val verseListState by viewModel.verseListState.collectAsStateWithLifecycle()
    val canPaginate by viewModel.canPaginate.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    VerseListScaffold(
        chapterUI = chapterUI,
        onNavigateUp = { navController.navigateUp() },
        scrollBehavior = scrollBehavior,
        verseListState = verseListState,
        onLoadNextVerses = {
            viewModel.loadNextVersesForChapter(
                chapter = chapterUI.toChapter()
            )
        },
        canPaginate = canPaginate
    )
}

@Composable
fun VerseListScaffold(
    chapterUI: ChapterUI,
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    verseListState: VerseListUIState,
    onLoadNextVerses: () -> Unit,
    canPaginate: Boolean
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Column(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = chapterUI.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = chapterUI.translatedName,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            VerticalDivider(modifier = Modifier.padding(all = 8.dp))
                            Text(
                                text = chapterUI.toChapter().revelationPlace.replaceFirstChar {
                                    it.uppercase()
                                },
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        when (verseListState) {
            VerseListUIState.Idle -> onLoadNextVerses()

            VerseListUIState.Loading -> CircularProgress(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            is VerseListUIState.Success -> VerseList(
                versesUI = verseListState.verses,
                canPaginate = canPaginate,
                onLoadNextVerses = onLoadNextVerses,
                modifier = Modifier.padding(innerPadding)
            )

            is VerseListUIState.Failure -> TODO()
        }
    }
}

@Composable
fun VerseList(
    versesUI: VersesUI,
    canPaginate: Boolean,
    onLoadNextVerses: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 4.dp)
                .testTag("verse_list")
        ) {
            itemsIndexed(versesUI.verseList) { index, verse ->
                VerseItem(
                    verseNo = index + 1,
                    verse = verse,
                    modifier = Modifier.fillMaxWidth(),
                    isLastVerse = index == versesUI.verseList.lastIndex
                )
            }
            item {
                if (canPaginate) {
                    CircularProgress(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                    onLoadNextVerses()
                }
            }
        }
    }
}

@Composable
fun VerseItem(
    verseNo: Int,
    verse: Verse,
    modifier: Modifier = Modifier,
    isLastVerse: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = verse.arabic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = NaskhFont,
                fontSize = 32.sp,
                textDirection = TextDirection.Rtl,
                lineHeight = 48.sp,
            )
        )
        Text(
            text = "$verseNo. ${verse.translated}",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
        if (!isLastVerse) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun VerseListScaffoldPreview() {
    EasyQuranTheme {
        VerseListScaffold(
            chapterUI = previewChapter,
            onNavigateUp = {},
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            verseListState = VerseListUIState.Success(previewChapterVerseUI),
            canPaginate = false,
            onLoadNextVerses = {}
        )
    }
}

internal val previewChapterVerseUI = ChapterVerse(
    chapterId = 1,
    chapterName = "Al-Fatihah",
    chapterTranslatedName = "The Opener",
    chapterRevelationPlace = "makkah",
    canPaginate = false,
    verseList = listOf(
        Verse(
            verseKey = "1:1",
            arabic = "بِسۡمِ اللهِ الرَّحۡمٰنِ الرَّحِيۡمِ",
            translated = "With the name of Allah, the All-Merciful, the Very-Merciful."
        ),
        Verse(
            verseKey = "1:2",
            arabic = "اَلۡحَمۡدُ لِلّٰهِ رَبِّ الۡعٰلَمِيۡنَۙ‏",
            translated = "Praise belongs to Allah, the Lord of all the worlds."
        ),
        Verse(
            verseKey = "1:3",
            arabic = "الرَّحۡمٰنِ الرَّحِيۡمِۙ‏",
            translated = "the All-Merciful, the Very Merciful."
        ),
        Verse(
            verseKey = "1:4",
            arabic = "مٰلِكِ يَوۡمِ الدِّيۡنِؕ‏",
            translated = "the Master of the Day of Requital."
        ),
        Verse(
            verseKey = "1:5",
            arabic = "اِيَّاكَ نَعۡبُدُ وَاِيَّاكَ نَسۡتَعِيۡنُؕ‏",
            translated = "You alone do we worship, and from You alone do we seek help."
        ),
        Verse(
            verseKey = "1:6",
            arabic = "اِهۡدِنَا الصِّرَاطَ الۡمُسۡتَقِيۡمَۙ‏",
            translated = "Take us on the straight path."
        ),
        Verse(
            verseKey = "1:7",
            arabic = "صِرَاطَ الَّذِيۡنَ اَنۡعَمۡتَ عَلَيۡهِمۡ ۙ‏ غَيۡرِ الۡمَغۡضُوۡبِ عَلَيۡهِمۡ وَلَا الضَّآلِّيۡنَ‏",
            translated = "the path of those on whom You have bestowed Your Grace, not of those who have incurred Your wrath, nor of those who have gone astray."
        )
    )
).toVersesUI()