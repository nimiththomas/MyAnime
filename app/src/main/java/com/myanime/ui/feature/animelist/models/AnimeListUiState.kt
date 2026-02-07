package com.myanime.ui.feature.animelist.models

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Represents the state of the Anime list screen.
 */
sealed class AnimeListUiState {
    object Loading : AnimeListUiState()
    data class Success(val animeFlow: Flow<PagingData<Anime>>) : AnimeListUiState()
    data class Error(val message: String) : AnimeListUiState()
}