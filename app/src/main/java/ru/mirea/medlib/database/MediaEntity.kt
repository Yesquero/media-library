package ru.mirea.medlib.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.mirea.medlib.domain.FilmCategory
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.network.dto.Country
import ru.mirea.medlib.network.dto.Genre

@Entity(tableName = "media_entity")
data class MediaEntity(
    @PrimaryKey
    val kinopoiskId: Long,
    val imdbId: String,
    val nameRu: String,
    val nameEn: String?,
    val nameOriginal: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingKinopoisk: Long,
    val ratingKinopoiskVoteCount: Long,
    val ratingImdb: Double,
    val ratingImdbVoteCount: Long,
    val webUrl: String,
    val year: Long,
    val filmLength: Long,
    val slogan: String?,
    val description: String,
    val shortDescription: String?,
    val editorAnnotation: String?,
    val type: FilmCategory,
    val ratingAgeLimits: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val startYear: Long,
    val endYear: Long,
    val serial: Boolean,
    val shortFilm: Boolean,
)

data class MediaEntityWithEpisode(
    @Embedded val mediaEntity: MediaEntity,
    @Relation(
        parentColumn = "kinopoiskId",
        entityColumn = "fkFilmId"
    )
    val episodes: List<Episode>
)

fun MediaEntityWithEpisode.asDomainModel(): MediaDetails {
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
        episodes = episodes.map { it -> it.asDomainModel() }
    )
}
