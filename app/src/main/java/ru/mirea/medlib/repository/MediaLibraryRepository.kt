package ru.mirea.medlib.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mirea.medlib.database.MedLibDatabase
import ru.mirea.medlib.database.asDomainModel
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.network.KinopoiskService
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.network.dto.FilmSearchDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaLibraryRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService,
    private val database: MedLibDatabase
) : BaseRepository("MedialLibraryRepository") {

    val library: LiveData<List<MediaDetails>> =
        Transformations.map(database.mediaEntityDao.getAllWithEpisode()) { list ->
            list.map { it ->
                it.asDomainModel()
            }
        }

    suspend fun keywordSearch(keyword: String): Flow<ResultWrapper<List<FilmSearchDto>>> {
        return flow {
            emit(ResultWrapper.Loading())
            emit(safeApiCall { kinopoiskService.searchByKeyword(keyword, 1) })
        }
    }
}