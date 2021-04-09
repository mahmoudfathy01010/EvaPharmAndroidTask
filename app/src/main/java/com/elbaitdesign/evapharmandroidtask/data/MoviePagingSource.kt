package com.elbaitdesign.evapharmandroidtask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.elbaitdesign.evapharmandroidtask.api.MovieApiName
import com.elbaitdesign.evapharmandroidtask.api.MovieApiService
import com.elbaitdesign.evapharmandroidtask.data.MovieRepository.Companion.NETWORK_PAGE_SIZE
import com.elbaitdesign.evapharmandroidtask.model.Movie
import java.io.IOException

private const val MOVIE_START_PAGING_INDEX = 1

class MoviePagingSource(
        private val service: MovieApiService,
        private val apiKeyQuery:String,
        private val apiName: MovieApiName,
        private val searchQuery:String?

): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key?: MOVIE_START_PAGING_INDEX
        return try {
            val response =when(apiName) {
                MovieApiName.NOW_PLAYING ->   service.getNowPlayingMovies(apiKeyQuery, position)
                MovieApiName.TOP_RATED->   service.getTopRatedMovies(apiKeyQuery,  position)
                else -> service.searchMovie(apiKeyQuery,searchQuery,position)
            }
            val movies = response.result
            val nextKey = if(movies.isEmpty()){
                null
            }
            else{
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIE_START_PAGING_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}