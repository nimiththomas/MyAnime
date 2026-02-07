package com.myanime.data.repository

import androidx.paging.PagingData
import com.myanime.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    /**
     * The paging data is loaded from the local database and automatically
     * fetched from the network when needed using [AnimeRemoteMediator].
     */
    fun getAnimeStream(): Flow<PagingData<AnimeEntity>>

    /**
     * Invalidates the current PagingSource to trigger a refresh.
     */
    suspend fun invalidateAnimePagingSource()

    /**
     * Returns an anime by its ID.
     */
    fun getAnime(id: Int): Flow<AnimeEntity?>
}

