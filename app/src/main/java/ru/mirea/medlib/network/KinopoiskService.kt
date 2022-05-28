package ru.mirea.medlib.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mirea.medlib.network.dto.FilmSearchDto

interface KinopoiskService {

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") pages: Int
    ): Response<List<FilmSearchDto>>
}