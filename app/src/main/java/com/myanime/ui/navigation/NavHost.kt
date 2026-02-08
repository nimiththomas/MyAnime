package com.myanime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myanime.ui.feature.animedetail.AnimeDetailScreen
import com.myanime.ui.feature.animelist.AnimeListScreen

@Composable
fun MyAnimeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.AnimeList
    ) {
        composable<Screen.AnimeList> {
            AnimeListScreen(
                onAnimeClick = { animeId ->
                    navController.navigate(
                        Screen.AnimeDetail(
                            animeId
                        )
                    )
                },
            )
        }
        composable<Screen.AnimeDetail> {
            AnimeDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}