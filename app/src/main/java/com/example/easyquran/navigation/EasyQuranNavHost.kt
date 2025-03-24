package com.example.easyquran.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.easyquran.ui.chapters.ChapterListScreen
import com.example.easyquran.ui.chapters.ChapterUI
import com.example.easyquran.ui.verses.VerseListScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Composable
fun EasyQuranNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.ChapterList,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable<Route.ChapterList> {
            ChapterListScreen(
                navController = navController
            )
        }

        composable<Route.VerseList>(
            typeMap = mapOf(
                typeOf<ChapterUI>() to ChapterNavType.chapterType
            ),
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val arguments = it.toRoute<Route.VerseList>()

            VerseListScreen(
                navController = navController,
                chapterUI = arguments.chapter
            )
        }
    }
}

sealed interface Route {
    @Serializable
    data object ChapterList

    @Serializable
    data class VerseList(val chapter: ChapterUI)
}