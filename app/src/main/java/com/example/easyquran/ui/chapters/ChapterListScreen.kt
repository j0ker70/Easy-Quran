package com.example.easyquran.ui.chapters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.easyquran.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterListScreen(
    viewModel: ChapterListViewModel = viewModel()
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val chapterListUIState by viewModel.chaptersListState.collectAsStateWithLifecycle()

    ChapterListScaffold(
        scrollBehavior = scrollBehavior,
        chapterListUIState = chapterListUIState,
        onLoadChapters = { viewModel.loadChapters() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChapterListScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    chapterListUIState: ChapterListUIState,
    onLoadChapters: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.makkah),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    )
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

        when (chapterListUIState) {

            ChapterListUIState.Idle -> onLoadChapters()

            ChapterListUIState.Loading -> {
                ChapterLoading()
            }

            is ChapterListUIState.Success -> {
                ChapterList(
                    chapterList = chapterListUIState.chapterList,
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
fun ChapterLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ChapterList(
    chapterList: List<ChapterUI>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(chapterList) { chapter ->
            SingleChapter(
                chapter = chapter,
                modifier = Modifier.fillMaxWidth(),
            )
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun ChaptersListPreview() {
    ChapterListScaffold(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        chapterListUIState = ChapterListUIState.Success(
            (1..100).map {
                previewChapter.copy(id = it.toString())
            }
        ),
        onLoadChapters = {}
    )
}
