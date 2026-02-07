package com.myanime.di

import com.myanime.data.local.MyAnimeDatabase
import com.myanime.data.remote.AnimeApiService
import com.myanime.data.repository.AnimeRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediatorModule {

    @Provides
    fun provideAnimeRemoteMediator(
        apiService: AnimeApiService,
        db: MyAnimeDatabase,
    ): AnimeRemoteMediator {
        return AnimeRemoteMediator(
            db = db,
            api = apiService,
        )
    }
}
