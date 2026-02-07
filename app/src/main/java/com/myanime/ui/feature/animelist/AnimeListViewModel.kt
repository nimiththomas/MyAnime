package com.myanime.ui.feature.animelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.myanime.core.NetworkObserver
import com.myanime.data.local.entity.AnimeEntity
import com.myanime.data.repository.AnimeRepository
import com.myanime.ui.feature.animelist.models.Anime
import com.myanime.ui.feature.animelist.models.AnimeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val repository: AnimeRepository,
    networkObserver: NetworkObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeListUiState>(AnimeListUiState.Loading)
    val uiState: StateFlow<AnimeListUiState> = _uiState.asStateFlow()

    val isOnline = networkObserver.isOnline
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), replay = 1)

    init {
        fetchLocalAnime()
    }

    private fun fetchLocalAnime() {
        viewModelScope.launch {
            try {
                val pagingFlow = repository.getAnimeStream()
                    .map { pagingData -> pagingData.map { it.toUiModel() } }
                    .cachedIn(viewModelScope)

                _uiState.value = AnimeListUiState.Success(pagingFlow)
            } catch (e: Exception) {
                _uiState.value = AnimeListUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}


/**
 * Maps DTO from DB/network to UI model
 */
private fun AnimeEntity.toUiModel(): Anime {
    return Anime(
        id = id,
        title = title,
        episodes = episodes,
        rating = rating,
        imageUrl = imageSmall,
        rank = rank
    )
}
