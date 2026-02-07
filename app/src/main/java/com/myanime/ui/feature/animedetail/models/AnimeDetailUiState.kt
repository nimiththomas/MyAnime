package com.myanime.ui.feature.animedetail.models

sealed interface AnimeDetailUiState {
    data class Success(val anime: AnimeDetail) : AnimeDetailUiState
    data class Error(val message: String) : AnimeDetailUiState
    object Loading : AnimeDetailUiState
}