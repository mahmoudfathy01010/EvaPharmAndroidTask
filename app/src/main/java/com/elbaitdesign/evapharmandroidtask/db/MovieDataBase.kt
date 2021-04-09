package com.elbaitdesign.evapharmandroidtask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elbaitdesign.evapharmandroidtask.model.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDataBase : RoomDatabase(){
    abstract val movieDao: MovieDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDataBase?=null
        fun getInstance(context: Context): MovieDataBase {
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        MovieDataBase::class.java,
                        "movie_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }


}