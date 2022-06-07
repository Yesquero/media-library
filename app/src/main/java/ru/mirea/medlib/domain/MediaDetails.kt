package ru.mirea.medlib.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mirea.medlib.network.dto.Country
import ru.mirea.medlib.network.dto.Genre

@Parcelize
data class MediaDetails(
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
    val episodes: List<EpisodeDetails>,
    val staffList: List<StaffDetails>
) : Parcelable {
    fun getFilterString(): String {
        return nameEn?.lowercase() +
                nameRu?.lowercase() +
                nameOriginal?.lowercase() +
                genres.joinToString { it.genre.lowercase() }
    }

    companion object {
        fun getDirectors(data: MediaDetails): String =
            getStaffHelper(data, StaffProfession.DIRECTOR)

        fun getProducers(data: MediaDetails): String =
            getStaffHelper(data, StaffProfession.PRODUCER)

        fun getWriters(data: MediaDetails): String = getStaffHelper(data, StaffProfession.WRITER)

        fun getOperators(data: MediaDetails): String =
            getStaffHelper(data, StaffProfession.OPERATOR)

        fun getActors(data: MediaDetails): String = getStaffHelper(data, StaffProfession.ACTOR)

        fun getComposers(data: MediaDetails): String =
            getStaffHelper(data, StaffProfession.COMPOSER)

        fun getDesigners(data: MediaDetails): String = getStaffHelper(data, StaffProfession.DESIGN)

        fun getEditors(data: MediaDetails): String = getStaffHelper(data, StaffProfession.EDITOR)

        private fun getStaffHelper(data: MediaDetails, filterItem: StaffProfession) =
            data.staffList.filter { it.professionKey == filterItem }
                .joinToString(", ",
                    transform = { staffDetails ->
                        nameFormatterHelper(staffDetails)
                    })

        private fun nameFormatterHelper(staffDetails: StaffDetails): String {
            return if (staffDetails.nameRu != null && staffDetails.nameRu != "") {
                staffDetails.nameRu
            } else if (staffDetails.nameEn != null && staffDetails.nameEn != "") {
                staffDetails.nameEn
            } else {
                "Lol noname"
            }
        }
    }
}
