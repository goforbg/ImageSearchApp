package com.codinginflow.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.Constants.UNSPLASH_DEFAULT_QUERY
import com.codinginflow.imagesearchapp.data.UnsplashRepository

class GalleryViewModel @ViewModelInject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "GalleryViewModel"
    }

    private val currentQuery = MutableLiveData(UNSPLASH_DEFAULT_QUERY)

    val photos = currentQuery.switchMap {
        //Switch map will take latest query param. Default value is set in current query,
        //then using fun "searchPhotos" it will change. switch map will execute query for latest.
        unsplashRepository.getSearchResults(it)
            .cachedIn(viewModelScope) //Caching results in viewmodel scope
        // so, if device is rotated or something you can retain.
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

}