package ru.mirea.medlib.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.mirea.medlib.domain.MediaDetails
import ru.mirea.medlib.repository.MediaLibraryRepository
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    private val mediaLibraryRepository: MediaLibraryRepository
) : ViewModel() {

    val mediaList: LiveData<List<MediaDetails>> = mediaLibraryRepository.library
}