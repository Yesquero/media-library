package ru.mirea.medlib.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MediaEntity::class, Episode::class, StaffEntity::class, MediaStaffCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CountryConverter::class, GenreConverter::class)
abstract class MedLibDatabase : RoomDatabase() {
    abstract val mediaLibraryDao: MediaLibraryDao
}