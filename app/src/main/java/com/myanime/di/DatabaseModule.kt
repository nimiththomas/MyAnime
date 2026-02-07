package com.myanime.di

import android.content.Context
import androidx.room.Room
import com.myanime.data.local.MyAnimeDatabase
import com.myanime.data.local.dao.AnimeDao
import com.myanime.data.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyAnimeDatabase =
        Room.databaseBuilder(
            context,
            MyAnimeDatabase::class.java,
            "myanime_db"
        ).fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideAnimeDao(db: MyAnimeDatabase): AnimeDao = db.animeDao()

    @Provides
    fun provideRemoteKeysDao(db: MyAnimeDatabase): RemoteKeysDao = db.remoteKeysDao()
}
