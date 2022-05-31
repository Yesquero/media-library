package ru.mirea.medlib.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mirea.medlib.network.dto.FilmDetailsDto
import ru.mirea.medlib.network.dto.FilmSearchResponse
import ru.mirea.medlib.network.dto.SeasonResponse
import ru.mirea.medlib.network.dto.StaffDto

interface KinopoiskService {

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") pages: Int
    ): Response<FilmSearchResponse>

    @GET("/api/v2.2/films/{id}")
    suspend fun getDetails(@Path("id") id: Long): Response<FilmDetailsDto>

    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSeasonDetails(@Path("id") id: Long): Response<SeasonResponse>

    @GET("/api/v1/staff")
    suspend fun getFilmStaff(@Query("filmId") id: Long): Response<List<StaffDto>>
}