package ru.mirea.medlib.network.dto

data class FilmSearchResponse(
    val filmId: Long,
    val nameRu: String,
    val nameEn: String,
    val type: String,
    val year: String,
    val description: String,
    val filmLength: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String,
    val ratingVoteCount: Long,
    val posterUrl: String,
    val posterUrlPreview: String,
)

data class Country(
    val country: String,
)

data class Genre(
    val genre: String,
)

