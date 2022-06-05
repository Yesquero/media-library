package ru.mirea.medlib.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.network.dto.FilmSearchResponse
import ru.mirea.medlib.repository.MediaLibraryRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaLibraryRepository: MediaLibraryRepository
) : ViewModel() {

    private val _searchResult: MutableLiveData<ResultWrapper<FilmSearchResponse>> =
        MutableLiveData()
    val searchResult: LiveData<ResultWrapper<FilmSearchResponse>> = _searchResult

    fun getSearchResults(keyword: String) {
        viewModelScope.launch {
            mediaLibraryRepository.keywordSearch(keyword).collect { it ->
                _searchResult.value = it
            }
        }
    }
}