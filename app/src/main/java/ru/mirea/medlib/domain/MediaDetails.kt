package ru.mirea.medlib.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mirea.medlib.network.dto.Country
import ru.mirea.medlib.network.dto.Genre

@Parcelize
data class MediaDetails(
    val kinopoiskId: Long,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String,
    val ratingKinopoisk: Double?,
    val ratingKinopoiskVoteCount: Long,
    val ratingImdb: Double?,
    val ratingImdbVoteCount: Long,
    val webUrl: String,
    val year: Long,
    val filmLength: Long?,
    val slogan: String?,
    val description: String?,
    val shortDescription: String?,
    val editorAnnotation: String?,
    val type: FilmCategory,
    val ratingAgeLimits: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val startYear: Long?,
    val endYear: Long?,
    val serial: Boolean,
    val shortFilm: Boolean,
    val episodes: List<EpisodeDetails>
) : Parcelable
