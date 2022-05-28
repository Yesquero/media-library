package ru.mirea.medlib.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mirea.medlib.network.dto.Genre

@Parcelize
data class FilmSearchResult(
    val filmId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val genres: List<Genre>,
    val rating: String?,
    val posterUrlPreview: String,
) : Parcelable
