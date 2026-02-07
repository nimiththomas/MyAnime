package com.myanime.ui.feature.animelist.models

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double?,
    val episodes: Int?,
    val rank: Int?
)