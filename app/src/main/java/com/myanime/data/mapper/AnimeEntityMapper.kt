package com.myanime.data.mapper

import com.myanime.data.local.entity.AnimeEntity
import com.myanime.data.remote.AnimeDto

fun AnimeDto.toEntity() = AnimeEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    episodes = episodes,
    rating = rating,
    imageSmall = images.webp.imageUrl,
    imageLarge = images.webp.largeImageUrl,
    trailerUrl = trailer?.embedUrl,
    genres = genres.joinToString { it.name },
    rank = rank
)