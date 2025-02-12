@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.easyquran.ui.verses

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.easyquran.model.domain.Verses
import com.example.easyquran.ui.chapters.ChapterUI
import com.example.easyquran.ui.chapters.previewChapter
import com.example.easyquran.ui.chapters.toChapter
import com.example.easyquran.ui.components.CircularProgress
import com.example.easyquran.ui.theme.EasyQuranTheme

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
                            VerticalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            Text(
                                text = chapterUI.toChapter().revelationPlace.uppercase(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Image(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(32.dp)
                        )
                    }
                },
                actions = {
                    Image(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
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
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(versesUI.verseList) { index, verse ->
            VerseItem(index + 1, verse)
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

@Composable
fun VerseItem(verseId: Int, verseText: String) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = verseId.toString(),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 16.sp
                ),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = verseText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier.padding(top = 4.dp)
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
            verseListState = VerseListUIState.Success(previewVersesUI),
            canPaginate = false,
            onLoadNextVerses = {}
        )
    }
}

internal val previewVersesUI = Verses(
    chapterId = 1,
    chapterName = "Al-Fatihah",
    chapterTranslatedName = "The Opener",
    chapterRevelationPlace = "makkah",
    canPaginate = false,
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