package ru.mirea.medlib.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.repository.MediaLibraryRepository
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val repository: MediaLibraryRepository
) : ViewModel() {

    private val _mediaItem: MutableLiveData<MediaDetails> = MutableLiveData()
    val mediaItem: LiveData<MediaDetails> = _mediaItem

    fun setMediaEntity(mediaDetails: MediaDetails) {
        _mediaItem.value = mediaDetails
    }
}