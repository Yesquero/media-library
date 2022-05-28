package ru.mirea.medlib.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.mirea.medlib.database.MedLibDatabase
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.network.KinopoiskService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaLibraryRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService,
    private val database: MedLibDatabase
) : BaseRepository("MedialLibraryRepository") {

    val library: LiveData<List<MediaDetails>> =
        Transformations.map(database.mediaEntityDao.getAllWithEpisode()) { it ->
            it.asDomainModel()
        }
}