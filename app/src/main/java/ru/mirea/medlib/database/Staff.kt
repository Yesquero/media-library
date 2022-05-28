package ru.mirea.medlib.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mirea.medlib.domain.StaffDetails
import ru.mirea.medlib.domain.StaffProfession

@Entity(tableName = "staff")
data class Staff(
    @PrimaryKey
    val staffId: Long,
    val nameRu: String,
    val nameEn: String,
    val description: String,
    val posterUrl: String,
    val professionText: String,
    val professionKey: StaffProfession,
)

fun Staff.asDomainModel(): StaffDetails {
    return StaffDetails(
        staffId = staffId,
        nameRu = nameRu,
        nameEn = nameEn,
        description = description,
        posterUrl = posterUrl,
        professionText = professionText,
        professionKey = professionKey
    )
}
