package com.elbaitdesign.evapharmandroidtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elbaitdesign.evapharmandroidtask.data.MovieRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MovieRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}