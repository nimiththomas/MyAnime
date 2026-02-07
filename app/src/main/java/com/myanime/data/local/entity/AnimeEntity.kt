package com.myanime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val rating: Double?,
    val imageSmall: String,
    val imageLarge: String,
    val trailerUrl: String?,
    val genres: String,
    val rank: Int?,
)