@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.easyquran.ui.chapters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.easyquran.R
import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.navigation.Route
import com.example.easyquran.ui.components.CircularProgress
import com.example.easyquran.ui.theme.EasyQuranTheme

@Composable
fun ChapterListScreen(
    navController: NavController,
    viewModel: ChapterListViewModel = hiltViewModel()
) {
    val chapterListUIState by viewModel.chaptersListState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ChapterListScaffold(
        scrollBehavior = scrollBehavior,
        chapterListUIState = chapterListUIState,
        onLoadChapters = { viewModel.loadChapters() },
        onChapterClick = { chapterUI ->
            navController.navigate(route = Route.VerseList(chapterUI))
        }
    )
}

@Composable
private fun ChapterListScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    chapterListUIState: ChapterListUIState,
    onLoadChapters: () -> Unit,
    onChapterClick: (ChapterUI) -> Unit,
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
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.muslim),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(32.dp)
                    )
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

        when (chapterListUIState) {
            ChapterListUIState.Idle -> onLoadChapters()

            ChapterListUIState.Loading -> {
                CircularProgress(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            is ChapterListUIState.Success -> {
                ChapterList(
                    chapterList = chapterListUIState.chapterList,
                    onChapterClick = onChapterClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is ChapterListUIState.Failure -> {
                // TODO
            }
        }
    }
}

@Composable
fun ChapterList(
    chapterList: List<ChapterUI>,
    onChapterClick: (ChapterUI) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("chapter_list")
        ) {
            itemsIndexed(chapterList) { index, chapter ->
                SingleChapter(
                    chapter = chapter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChapterClick(chapter) },
                    isLastChapter = index == chapterList.lastIndex
                )
            }
        }
    }
}

@Composable
fun SingleChapter(
    chapter: ChapterUI,
    modifier: Modifier = Modifier,
    isLastChapter: Boolean = false
) {
    Column {
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
                modifier = Modifier.size(40.dp)
            )
        }
        if (!isLastChapter) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outline
            )
        }
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
        ChapterListScaffold(
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
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