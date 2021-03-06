package ru.mirea.medlib.database

import androidx.room.*
import ru.mirea.medlib.domain.FilmCategory
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.network.dto.Country
import ru.mirea.medlib.network.dto.Genre

@Entity(tableName = "media_entity")
data class MediaEntity(
    @PrimaryKey
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
)

data class MediaEntityFull(
    @Embedded val mediaEntity: MediaEntity,

    @Relation(
        parentColumn = "kinopoiskId",
        entityColumn = "film_id"
    )
    val episodes: List<Episode>,

    @Relation(
        parentColumn = "kinopoiskId",
        entityColumn = "internalId",
        associateBy = Junction(MediaStaffCrossRef::class)
    )
    val staffList: List<StaffEntity>
)

fun List<MediaEntityFull>.asDomainModel() = map { it.asDomainModel() }

fun MediaEntityFull.asDomainModel(): MediaDetails {
    return MediaDetails(
        kinopoiskId = mediaEntity.kinopoiskId,
        imdbId = mediaEntity.imdbId,
        nameRu = mediaEntity.nameRu,
        nameEn = mediaEntity.nameEn,
        nameOriginal = mediaEntity.nameOriginal,
        posterUrl = mediaEntity.posterUrl,
        posterUrlPreview = mediaEntity.posterUrlPreview,
        ratingKinopoisk = mediaEntity.ratingKinopoisk,
        ratingKinopoiskVoteCount = mediaEntity.ratingKinopoiskVoteCount,
        ratingImdb = mediaEntity.ratingImdb,
        ratingImdbVoteCount = mediaEntity.ratingImdbVoteCount,
        webUrl = mediaEntity.webUrl,
        year = mediaEntity.year,
        filmLength = mediaEntity.filmLength,
        slogan = mediaEntity.slogan,
        description = mediaEntity.description,
        shortDescription = mediaEntity.shortDescription,
        editorAnnotation = mediaEntity.editorAnnotation,
        type = mediaEntity.type,
        ratingAgeLimits = mediaEntity.ratingAgeLimits,
        countries = mediaEntity.countries,
        genres = mediaEntity.genres,
        startYear = mediaEntity.startYear,
        endYear = mediaEntity.endYear,
        serial = mediaEntity.serial,
        shortFilm = mediaEntity.shortFilm,
        episodes = episodes.asDomainModel(),
        staffList = staffList.asDomainModel()
    )
}
