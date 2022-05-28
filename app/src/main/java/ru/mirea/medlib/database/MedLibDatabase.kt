package ru.mirea.medlib.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MediaEntity::class, Episode::class], version = 1)
@TypeConverters(CountryConverter::class, GenreConverter::class)
abstract class MedLibDatabase : RoomDatabase() {
    abstract val mediaEntityDao: MediaEntityDao
}