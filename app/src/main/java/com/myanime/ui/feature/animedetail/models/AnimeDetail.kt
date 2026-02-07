package com.myanime.ui.feature.animedetail.models

import com.myanime.data.local.entity.AnimeEntity

data class AnimeDetail(
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

fun AnimeEntity.toAnimeDetail() = AnimeDetail(
    id = id,
    title = title,
    synopsis = synopsis,
    episodes = episodes,
    rating = rating,
    imageSmall = imageSmall,
    imageLarge = imageLarge,
    trailerUrl = trailerUrl,
    genres = genres,
    rank = rank
)
