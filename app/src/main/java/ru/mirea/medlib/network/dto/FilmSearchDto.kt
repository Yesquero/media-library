package ru.mirea.medlib.network.dto

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import ru.mirea.medlib.domain.FilmSearchResult

@JsonClass(generateAdapter = true)
data class FilmSearchResponse(
    val keyword: String?,
    val pagesCount: Int,
    val searchFilmsCountResult: Int,
    val films: List<FilmSearchDto>
)

@JsonClass(generateAdapter = true)
data class FilmSearchDto(
    val filmId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val type: String,
    val year: String,
    val description: String?,
    val filmLength: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String?,
    val ratingVoteCount: Long,
    val posterUrl: String,
    val posterUrlPreview: String,
)

fun List<FilmSearchDto>.asDomainModel(): List<FilmSearchResult> {
    return map {
        FilmSearchResult(
            filmId = it.filmId,
            nameRu = it.nameRu,
            nameEn = it.nameEn,
            genres = it.genres,
            posterUrlPreview = it.posterUrlPreview,
            rating = it.rating
        )
    }
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Country(
    val country: String,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Genre(
    val genre: String,
) : Parcelable

