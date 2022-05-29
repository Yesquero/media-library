package ru.mirea.medlib.domain

data class EpisodeDetails(
    val episodeId: Long,
    val seasonNumber: Long,
    val episodeNumber: Long,
    val nameRu: String?,
    val nameEn: String?,
    val synopsis: String?,
    val releaseDate: String?,
)