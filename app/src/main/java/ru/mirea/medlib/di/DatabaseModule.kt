package ru.mirea.medlib.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mirea.medlib.database.MedLibDatabase
import ru.mirea.medlib.database.MediaEntityDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MedLibDatabase {
        return Room.databaseBuilder(
            appContext,
            MedLibDatabase::class.java,
            "MedLibDB"
        ).build()
    }

    @Provides
    fun provideMediaEntityDao(medLibDatabase: MedLibDatabase): MediaEntityDao {
        return medLibDatabase.mediaEntityDao
    }
}