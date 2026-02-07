package com.myanime.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myanime.data.local.dao.AnimeDao
import com.myanime.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AnimeRepositoryImpl @Inject constructor(
    private val animeDao: AnimeDao,
    private val remoteMediator: AnimeRemoteMediator
) : AnimeRepository {

    override fun getAnimeStream(): Flow<PagingData<AnimeEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 15,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { animeDao.pagingSource() }
        ).flow
    }

    override suspend fun invalidateAnimePagingSource() {
        animeDao.pagingSource().invalidate()
    }

    override fun getAnime(id: Int): Flow<AnimeEntity?> {
        return animeDao.getAnime(id)
    }
}
