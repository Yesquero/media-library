package ru.mirea.medlib.network.dto

import ru.mirea.medlib.domain.FilmCategory

data class FilmDetailsDto(
    val kinopoiskId: Long,
    val imdbId: String,
    val nameRu: String,
    val nameEn: String?,
    val nameOriginal: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val coverUrl: String?,
    val logoUrl: String?,
    val reviewsCount: Long,
    val ratingGoodReview: Double,
    val ratingGoodReviewVoteCount: Long,
    val ratingKinopoisk: Long,
    val ratingKinopoiskVoteCount: Long,
    val ratingImdb: Double,
    val ratingImdbVoteCount: Long,
    val ratingFilmCritics: Double?,
    val ratingFilmCriticsVoteCount: Long,
    val ratingAwait: Double,
    val ratingAwaitCount: Long,
    val ratingRfCritics: Long,
    val ratingRfCriticsVoteCount: Long,
    val webUrl: String,
    val year: Long,
    val filmLength: Long,
    val slogan: String?,
    val description: String,
    val shortDescription: String?,
    val editorAnnotation: String?,
    val isTicketsAvailable: Boolean,
    val productionStatus: String?,
    val type: FilmCategory,
    val ratingMpaa: String?,
    val ratingAgeLimits: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val startYear: Long,
    val endYear: Long,
    val serial: Boolean,
    val shortFilm: Boolean,
    val completed: Boolean,
    val hasImax: Boolean,
    val has3D: Boolean,
    val lastSync: String,
)
