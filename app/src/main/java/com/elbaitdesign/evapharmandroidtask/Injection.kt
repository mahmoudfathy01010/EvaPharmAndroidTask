package com.elbaitdesign.evapharmandroidtask

import androidx.lifecycle.ViewModelProvider
import com.elbaitdesign.evapharmandroidtask.api.MovieApi
import com.elbaitdesign.evapharmandroidtask.data.MovieRepository
import com.elbaitdesign.evapharmandroidtask.ui.ViewModelFactory

object Injection {
    private fun provideGithubRepository(): MovieRepository {
        return MovieRepository(MovieApi.retrofitService)
    }
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}