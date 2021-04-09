package com.elbaitdesign.evapharmandroidtask.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elbaitdesign.evapharmandroidtask.api.*
import com.elbaitdesign.evapharmandroidtask.db.MovieDao
import com.elbaitdesign.evapharmandroidtask.model.GenreApiResponse
import com.elbaitdesign.evapharmandroidtask.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val service: MovieApiService) {
    fun getMovies(apiKeyQuery: String, apiName: MovieApiName, searchQuery:String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(service,apiKeyQuery,apiName,searchQuery) }
        ).flow
    }

    suspend fun  insertFavouriteMovie(movie: Movie, database: MovieDao) {
        database.insertMovie(movie)
    }

     fun deleteFavouriteData(database: MovieDao) {
        database.deleteFavouriteData()
    }

    fun getFavouriteMovies(database: MovieDao): LiveData<List<Movie>> {
        return database.getFavouriteMovies()
    }

    suspend fun getGenres(apiKeyQuery: String): GenreApiResponse {
        return service.getGenres(apiKeyQuery)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}