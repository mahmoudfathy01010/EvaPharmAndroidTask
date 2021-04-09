package com.elbaitdesign.evapharmandroidtask.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elbaitdesign.evapharmandroidtask.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("select * from movie")
    fun getFavouriteMovies(): LiveData<List<Movie>>

    @Query("delete  from movie")
    fun deleteFavouriteData()
}