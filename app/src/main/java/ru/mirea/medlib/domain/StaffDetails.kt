package ru.mirea.medlib.domain

data class StaffDetails(
    val staffId: Long,
    val nameRu: String,
    val nameEn: String,
    val description: String,
    val posterUrl: String,
    val professionText: String,
    val professionKey: StaffProfession,
)