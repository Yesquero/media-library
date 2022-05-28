package ru.mirea.medlib.network.dto

data class SeasonResponse(
    val total: Long,
    val seasons: List<Season>,
)

data class Season(
    val number: Long,
    val episodes: List<EpisodeDto>,
)

data class EpisodeDto(
    val seasonNumber: Long,
    val episodeNumber: Long,
    val nameRu: String?,
    val nameEn: String?,
    val synopsis: String?,
    val releaseDate: String,
)
