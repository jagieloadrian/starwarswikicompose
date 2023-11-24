package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
        private val useCases: UseCases,
) : ViewModel() {
    private val _fetchedPhotoInfos = MutableStateFlow(FlickrResponse())
    val fetchedPhotoInfos = _fetchedPhotoInfos

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    init {
        fetchRecentPhotos()
    }

    fun fetchPhotoInfo(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCases.getSearchImagesUseCase(searchText = query)
                        .let { flickrResponse -> _fetchedPhotoInfos.value = flickrResponse }
            } catch (exc: Exception) {
                _fetchedPhotoInfos.value = FlickrResponse(message = exc.javaClass.simpleName)
            }
        }
    }

    fun fetchRecentPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCases.getRecentImagesUseCase().let { response -> _fetchedPhotoInfos.value = response }
            } catch (exc: Exception) {
                _fetchedPhotoInfos.value = FlickrResponse(message = exc.javaClass.simpleName)
            }
        }
    }
}