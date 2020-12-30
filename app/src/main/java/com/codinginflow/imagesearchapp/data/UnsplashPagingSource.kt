package com.codinginflow.imagesearchapp.data

import androidx.paging.PagingSource
import com.codinginflow.imagesearchapp.data.Constants.UNSPLASH_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException


class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {
    //First is page of Int, second is object used in the recyclerview (Not response object)


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        //params.key = page number, if null use starting index.
        //params.loadSize = per page which is set in caller repository as PagingConfig's first argument.
        //For example     fun getSearchResults(query: String) = Pager(PagingConfig(20))
        val position = params.key
            ?: UNSPLASH_STARTING_PAGE_INDEX //Which page we want to load. First it would be null,
        return try {
            val response = unsplashApi.searchPhotos(
                query,
                position,
                params.loadSize
            ) // Configure that value seperately it's basically how many results per page.
            val photos = response.results //Because we need results object inside response
            return LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            //No internet
            LoadResult.Error(e)
        } catch (e: HttpException) {
            //Server issue
            LoadResult.Error(e)
        }
    }

}