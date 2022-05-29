package ru.mirea.medlib.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.network.dto.FilmDetailsDto
import ru.mirea.medlib.repository.MediaLibraryRepository
import javax.inject.Inject

@HiltViewModel
class AddMediaViewModel @Inject constructor(
    private val mediaLibraryRepository: MediaLibraryRepository
) : ViewModel() {

    private val _detailsDtoResult: MutableLiveData<ResultWrapper<FilmDetailsDto>> =
        MutableLiveData()
    val detailsDtoResult: LiveData<ResultWrapper<FilmDetailsDto>> = _detailsDtoResult

    fun getMediaDetails(id: Long) {
        viewModelScope.launch {
            mediaLibraryRepository.getDetails(id).collect {
                _detailsDtoResult.value = it
            }
        }
    }

    fun saveMedia() {
        viewModelScope.launch(Dispatchers.IO) {
            _detailsDtoResult.value?.data?.let { mediaLibraryRepository.saveMedia(it) }
        }
    }
}