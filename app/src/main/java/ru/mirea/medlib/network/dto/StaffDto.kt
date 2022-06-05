package ru.mirea.medlib.network.dto

import com.squareup.moshi.JsonClass
import ru.mirea.medlib.database.StaffEntity
import ru.mirea.medlib.domain.StaffProfession

@JsonClass(generateAdapter = true)
data class StaffDto(
    val staffId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String?,
    val professionText: String,
    val professionKey: StaffProfession
)

fun StaffDto.asDatabaseModel() =
    StaffEntity(
        staffId = staffId,
        nameRu = nameRu,
        nameEn = nameEn,
        description = description,
        posterUrl = posterUrl,
        professionText = professionText,
        professionKey = professionKey,
    )

fun List<StaffDto>.asDatabaseModel() = map { it.asDatabaseModel() }