package com.myanime.ui.feature.animedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myanime.data.repository.AnimeRepository
import com.myanime.ui.feature.animedetail.models.AnimeDetailUiState
import com.myanime.ui.feature.animedetail.models.toAnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeDetailUiState>(AnimeDetailUiState.Loading)
    val uiState: StateFlow<AnimeDetailUiState> = _uiState.asStateFlow()

    fun fetchAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            repository.getAnime(animeId)
                .catch { e ->
                    _uiState.value = AnimeDetailUiState.Error(e.message ?: "An unknown error occurred")
                }
                .collect { animeEntity ->
                    if (animeEntity != null) {
                        _uiState.value = AnimeDetailUiState.Success(animeEntity.toAnimeDetail())
                    } else {
                        _uiState.value = AnimeDetailUiState.Error("Anime not found")
                    }
                }
        }
    }
}
