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
import ru.mirea.medlib.network.dto.allEpisodes
import ru.mirea.medlib.network.dto.asDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaLibraryRepository @Inject constructor(
    private val kinopoiskService: KinopoiskService,
    private val database: MedLibDatabase
) : BaseRepository("MedialLibraryRepository") {

    val library: LiveData<List<MediaDetails>> =
        Transformations.map(database.mediaLibraryDao.getAllWithEpisode()) {
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

    suspend fun saveMedia(dto: FilmDetailsDto): Flow<ResultWrapper<Unit>> {
        return flow<ResultWrapper<Unit>> {
            emit(ResultWrapper.Loading())

            // if media already exists return error
            if (database.mediaLibraryDao.mediaExists(dto.kinopoiskId)) {
                emit(ResultWrapper.Error("Media already saved!"))
                return@flow
            }

            // get staff list
            val staffResponse = safeApiCall { kinopoiskService.getFilmStaff(dto.kinopoiskId) }
            if (!staffResponse.isSuccessful()) {
                emit(ResultWrapper.Error(staffResponse.message!!))
                return@flow
            }

            // get episode list if one exists and save
            if (dto.serial) {
                val episodeResponse =
                    safeApiCall { kinopoiskService.getSeasonDetails(dto.kinopoiskId) }
                if (episodeResponse.isSuccessful()) {
                    database.mediaLibraryDao.insertWithEpisode(
                        dto.asDatabaseModel(),
                        staffResponse.data!!.asDatabaseModel(),
                        episodeResponse.data!!.items.allEpisodes().asDatabaseModel()
                    )
                    emit(ResultWrapper.Success(Unit))
                    return@flow
                } else {
                    emit(ResultWrapper.Error(episodeResponse.message!!))
                    return@flow
                }
            }

            // save if no episodes
            database.mediaLibraryDao.insert(
                dto.asDatabaseModel(),
                staffResponse.data!!.asDatabaseModel()
            )
            emit(ResultWrapper.Success(Unit))
        }.flowOn(Dispatchers.IO)
    }

    fun deleteMedia(mediaDetails: MediaDetails) {
        database.mediaLibraryDao.deleteMediaFull(mediaDetails)
    }
}