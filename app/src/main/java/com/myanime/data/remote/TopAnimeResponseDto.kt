package com.myanime.data.remote

import com.google.gson.annotations.SerializedName

data class TopAnimeResponseDto(
    @SerializedName("data")
    val data: List<AnimeDto>,
    @SerializedName("pagination")
    val pagination: PaginationDto,
)

data class PaginationDto(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int
)


data class AnimeDto(
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("episodes")
    val episodes: Int?,
    @SerializedName("score")
    val rating: Double?,
    @SerializedName("images")
    val images: ImagesDto,
    @SerializedName("trailer")
    val trailer: TrailerDto?,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("rank")
    val rank: Int?
)


data class ImagesDto(
    @SerializedName("webp")
    val webp: WebpImageDto
)

data class WebpImageDto(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("large_image_url")
    val largeImageUrl: String
)

data class TrailerDto(
    @SerializedName("embed_url")
    val embedUrl: String?
)


data class GenreDto(
    @SerializedName("name")
    val name: String
)
