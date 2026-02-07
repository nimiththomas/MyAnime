package com.myanime.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface AnimeApiService {
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int
    ): TopAnimeResponseDto
}