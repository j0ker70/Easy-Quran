package com.example.easyquran.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.easyquran.R
import com.example.easyquran.ui.chapters.ChapterListScreen
import com.example.easyquran.ui.chapters.ChapterUI
import com.example.easyquran.ui.verses.VerseListScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasyQuranNavHost(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.fillMaxSize(),
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

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ChapterListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<ChapterListRoute> {
                ChapterListScreen(
                    onChapterClick = { chapterUI ->
                        navController.navigate(route = VerseRoute(chapterUI))
                    }
                )
            }
            composable<VerseRoute>(
                typeMap = mapOf(
                    typeOf<ChapterUI>() to ChapterNavType.chapterType
                )
            ) {
                val arguments = it.toRoute<VerseRoute>()
                VerseListScreen(
                    chapterUI = arguments.chapter
                )
            }
        }
    }
}

@Serializable
data object ChapterListRoute

@Serializable
data class VerseRoute(
    val chapter: ChapterUI
)