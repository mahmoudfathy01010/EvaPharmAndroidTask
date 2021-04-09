package com.elbaitdesign.evapharmandroidtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elbaitdesign.evapharmandroidtask.api.MovieApiName
import com.elbaitdesign.evapharmandroidtask.data.MovieRepository
import com.elbaitdesign.evapharmandroidtask.db.MovieDao
import com.elbaitdesign.evapharmandroidtask.model.Genre
import com.elbaitdesign.evapharmandroidtask.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<Movie>>? = null
    private var currentApiName:MovieApiName? = null
    lateinit var favouriteMovies:LiveData<List<Movie>>
    private val _genres = MutableLiveData<List<Genre>>()
    val genres :LiveData<List<Genre>> = _genres
    fun getMovies(apiKeyQuery:String, apiName:MovieApiName, searchQuery: String?): Flow<PagingData<Movie>> {
        currentApiName = apiName
        val newResult: Flow<PagingData<Movie>> = repository.getMovies(apiKeyQuery,apiName,searchQuery)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getFavouriteMovies(database: MovieDao
    ) {
       favouriteMovies= repository.getFavouriteMovies(database)
    }

    suspend fun insertFavouriteMovie(movie: Movie, database: MovieDao){
        withContext(Dispatchers.IO){
            repository.insertFavouriteMovie(movie,database)
        }

    }

    suspend fun deleteFavouriteList(database: MovieDao) {
        withContext(Dispatchers.IO){
            repository.deleteFavouriteData(database)
        }
    }

    suspend fun getGenres(apiKeyQuery:String){
       _genres.value = repository.getGenres(apiKeyQuery).genres
    }
}