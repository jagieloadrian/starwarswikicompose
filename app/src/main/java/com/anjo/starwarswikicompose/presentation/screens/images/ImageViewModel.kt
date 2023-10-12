package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anjo.starwarswikicompose.domain.model.FlickrResponse
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
        private val flickrApiImpl: FlickrApiImpl
):ViewModel(){
    private val _fetchedPhotoInfos = MutableStateFlow<FlickrResponse>(FlickrResponse())
    val fetchedPhotoInfos = _fetchedPhotoInfos

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchPhotoInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            flickrApiImpl.getSearchPhotosInfo(searchText = "nature").let { flickrResponse -> _fetchedPhotoInfos.value = flickrResponse }
        }
    }
}