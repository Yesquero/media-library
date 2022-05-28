package ru.mirea.medlib.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonResponse(
    val total: Long,
    val seasons: List<Season>,
)

@JsonClass(generateAdapter = true)
data class Season(
    val number: Long,
    val episodes: List<EpisodeDto>,
)

@JsonClass(generateAdapter = true)
data class EpisodeDto(
    val seasonNumber: Long,
    val episodeNumber: Long,
    val nameRu: String?,
    val nameEn: String?,
    val synopsis: String?,
    val releaseDate: String,
)
