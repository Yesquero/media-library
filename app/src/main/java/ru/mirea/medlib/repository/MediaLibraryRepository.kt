package ru.mirea.medlib.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.mirea.medlib.database.MedLibDatabase
import ru.mirea.medlib.database.asDomainModel
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.network.KinopoiskService
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.network.dto.FilmDetailsDto
import ru.mirea.medlib.network.dto.FilmSearchResponse
import ru.mirea.medlib.network.dto.asDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaLibraryRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService,
    private val database: MedLibDatabase
) : BaseRepository("MedialLibraryRepository") {

    val library: LiveData<List<MediaDetails>> =
        Transformations.map(database.mediaEntityDao.getAllWithEpisode()) {
            it.asDomainModel()
        }

    suspend fun keywordSearch(keyword: String): Flow<ResultWrapper<FilmSearchResponse>> {
        return flow {
            emit(ResultWrapper.Loading())
            emit(safeApiCall { kinopoiskService.searchByKeyword(keyword, 1) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetails(id: Long): Flow<ResultWrapper<FilmDetailsDto>> {
        return flow {
            emit(ResultWrapper.Loading())
            emit(safeApiCall { kinopoiskService.getDetails(id) })
        }.flowOn(Dispatchers.IO)
    }

    fun saveMedia(filmDetailsDto: FilmDetailsDto) {
        if (filmDetailsDto.serial) {
            TODO("Load episodes and save, also figure out how to change ui")
        } else {
            database.mediaEntityDao.insert(filmDetailsDto.asDatabaseModel())
        }
    }
}