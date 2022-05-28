package ru.mirea.medlib.domain

import ru.mirea.medlib.network.dto.Genre

data class FilmSearchResult(
    val filmId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val genres: List<Genre>,
    val rating: String?,
    val posterUrlPreview: String,
) {
    companion object {
        fun formattedGenres(filmSearchResult: FilmSearchResult): String {
            return filmSearchResult.genres.joinToString(", ") { it -> it.genre }
        }
    }
}
