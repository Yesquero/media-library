package ru.mirea.medlib.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MediaEntity::class, Episode::class], version = 6, exportSchema = false)
@TypeConverters(CountryConverter::class, GenreConverter::class)
abstract class MedLibDatabase : RoomDatabase() {
    abstract val mediaLibraryDao: MediaLibraryDao
}