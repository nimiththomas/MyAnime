package com.myanime.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object AnimeList : Screen()

    @Serializable
    data class AnimeDetail(val animeId: Int) : Screen()
}