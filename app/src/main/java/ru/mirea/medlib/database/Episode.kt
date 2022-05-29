package ru.mirea.medlib.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.mirea.medlib.domain.EpisodeDetails

@Entity(
    tableName = "episode",
    foreignKeys = [
        ForeignKey(
            entity = MediaEntity::class,
            parentColumns = ["kinopoiskId"],
            childColumns = ["fkFilmId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Episode(
    @PrimaryKey(autoGenerate = true)
    var episodeId: Long = 0,
    // owning film
    var fkFilmId: Long = 0,
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
