package ru.mirea.medlib.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffDetails(
    val internalId: Long,
    val staffId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String?,
    val professionText: String,
    val professionKey: StaffProfession
) : Parcelable