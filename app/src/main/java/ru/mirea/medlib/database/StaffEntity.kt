package ru.mirea.medlib.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mirea.medlib.domain.StaffDetails
import ru.mirea.medlib.domain.StaffProfession

@Entity(tableName = "staff")
data class StaffEntity(
    @PrimaryKey(autoGenerate = true)
    var internalId: Long = 0,
    val staffId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String?,
    val professionText: String,
    val professionKey: StaffProfession,
)

@Entity(tableName = "media_staff_crossRef", primaryKeys = ["kinopoiskId", "internalId"])
data class MediaStaffCrossRef(
    val kinopoiskId: Long,
    val internalId: Long
)

fun StaffEntity.asDomainModel() =
    StaffDetails(
        internalId = internalId,
        staffId = staffId,
        nameRu = nameRu,
        nameEn = nameEn,
        description = description,
        posterUrl = posterUrl,
        professionText = professionText,
        professionKey = professionKey
    )

fun List<StaffEntity>.asDomainModel() = map { it.asDomainModel() }