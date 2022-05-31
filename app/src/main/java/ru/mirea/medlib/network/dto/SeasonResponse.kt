package ru.mirea.medlib.network.dto

import com.squareup.moshi.JsonClass
import ru.mirea.medlib.database.Episode

@JsonClass(generateAdapter = true)
data class SeasonResponse(
    val total: Long,
    val items: List<Season>,
)

fun List<Season>.allEpisodes(): List<EpisodeDto> = flatMap { it.episodes }

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
    val releaseDate: String?,
)

fun List<EpisodeDto>.asDatabaseModel(): List<Episode> = map {
    Episode(
        episodeNumber = it.episodeNumber,
        seasonNumber = it.seasonNumber,
        nameEn = it.nameEn,
        nameRu = it.nameRu,
        synopsis = it.synopsis,
        releaseDate = it.releaseDate
    )
}
