package com.codinginflow.imagesearchapp.data

import android.util.Log
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


}