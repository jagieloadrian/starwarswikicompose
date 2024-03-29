package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.loading
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.ERROR_UNAVAILABLE_INTERNET
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
open class ImageViewModel @Inject constructor(
        private val useCases: UseCases,
) : ViewModel() {
    private val _fetchedPhotoInfos = MutableStateFlow(FlickrResponse())
    open val fetchedPhotoInfos = _fetchedPhotoInfos.asStateFlow()

    private val _searchQuery = mutableStateOf("")
    open val searchQuery = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getRecentPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchedPhotoInfos.update {
                it.copy(stat = loading)
            }
            delay(2.seconds)
            fetchRecentPhotos()
        }
    }

    fun fetchPhotoInfo(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fetchedPhotoInfos.update {
                    val response = useCases.getSearchImagesUseCase(searchText = query)
                    it.copy(photos = response.photos, stat = response.stat, code = response.code,
                            message = response.message)
                }
            } catch (exc: Exception) {
                _fetchedPhotoInfos.update {
                    FlickrResponse(message = ERROR_UNAVAILABLE_INTERNET)
                }
            }
        }
    }

    fun fetchRecentPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fetchedPhotoInfos.update {
                    val response = useCases.getRecentImagesUseCase()
                    it.copy(photos = response.photos, stat = response.stat, code = response.code,
                            message = response.message)
                }
            } catch (exc: Exception) {
                _fetchedPhotoInfos.update {
                    FlickrResponse(message = ERROR_UNAVAILABLE_INTERNET)
                }
            }
        }
    }
}