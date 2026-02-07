package com.myanime.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.myanime.data.local.dao.AnimeDao
import com.myanime.data.local.dao.RemoteKeysDao
import com.myanime.data.local.entity.AnimeEntity
import com.myanime.data.local.entity.RemoteKeys


@Database(
    entities = [AnimeEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MyAnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}
