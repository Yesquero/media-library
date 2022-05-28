package ru.mirea.medlib.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mirea.medlib.domain.EpisodeDetails

@Entity(tableName = "episode")
data class Episode(
    @PrimaryKey(autoGenerate = true)
    val episodeId: Long,
    // owning film
    var fkFilmId: Long,
    val seasonNumber: Long,
    val episodeNumber: Long,
    val nameRu: String?,
    val nameEn: String?,
    val synopsis: String?,
    val releaseDate: String,
)

fun Episode.asDomainModel(): EpisodeDetails {
    return EpisodeDetails(
        episodeId = episodeId,
        seasonNumber = seasonNumber,
        episodeNumber = episodeNumber,
        nameRu = nameRu,
        nameEn = nameEn,
        synopsis = synopsis,
        releaseDate = releaseDate
    )
}

fun List<Episode>.asDomainModel(): List<EpisodeDetails> = map { it.asDomainModel() }
