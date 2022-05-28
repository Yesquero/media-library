package ru.mirea.medlib.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mirea.medlib.network.dto.FilmDetailsDto
import ru.mirea.medlib.network.dto.FilmSearchResponse

interface KinopoiskService {

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") pages: Int
    ): Response<FilmSearchResponse>

    @GET("/api/v2.2/films/{id}")
    suspend fun getDetails(@Path("id") id: Long): Response<FilmDetailsDto>
}