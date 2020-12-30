package com.codinginflow.imagesearchapp.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.codinginflow.imagesearchapp.data.Constants.UNSPLASH_MAX_PAGE_INDEX
import com.codinginflow.imagesearchapp.data.Constants.UNSPLASH_PER_PAGE_INDEX
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    companion object {
        private const val TAG = "UnsplashRepository"
    }

    init {
        Log.d(TAG, "creating Repo: with unsplash ${unsplashApi.hashCode()}")
    }

    fun getSearchResults(query: String) = Pager(
        PagingConfig(
            pageSize = UNSPLASH_PER_PAGE_INDEX,
            maxSize = UNSPLASH_MAX_PAGE_INDEX,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            UnsplashPagingSource(unsplashApi, query)
        }
    ).liveData

}