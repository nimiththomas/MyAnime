package com.myanime.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.myanime.data.local.MyAnimeDatabase
import com.myanime.data.local.entity.AnimeEntity
import com.myanime.data.local.entity.RemoteKeys
import com.myanime.data.mapper.toEntity
import com.myanime.data.remote.AnimeApiService

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val api: AnimeApiService,
    private val db: MyAnimeDatabase,
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            val response = api.getTopAnime(page)
            val entities = response.data.map { it.toEntity() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.animeDao().clearAll()
                }

                val keys = entities.map {
                    RemoteKeys(
                        animeId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.pagination.hasNextPage) page + 1 else null
                    )
                }

                db.remoteKeysDao().insertAll(keys)
                db.animeDao().insertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = !response.pagination.hasNextPage
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { anime ->
                db.remoteKeysDao().remoteKeysAnimeId(anime.id)
            }
    }
}
