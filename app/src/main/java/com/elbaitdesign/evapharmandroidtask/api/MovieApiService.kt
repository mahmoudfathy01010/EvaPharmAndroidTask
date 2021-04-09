package com.elbaitdesign.evapharmandroidtask.api

import com.elbaitdesign.evapharmandroidtask.model.GenreApiResponse
import com.elbaitdesign.evapharmandroidtask.model.MovieApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

enum class MovieApiName{
    NOW_PLAYING,TOP_RATED,SEARCH
}


private val logger= HttpLoggingInterceptor()
val client = OkHttpClient.Builder()
    .addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BASIC))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey : String,
                              @Query("page")page:Int
    ): MovieApiResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey : String,
                                    @Query("page")page:Int
    ): MovieApiResponse

    @GET("search/movie")
    suspend fun searchMovie(@Query("api_key") apiKey : String,
                            @Query("query")query:String?,
                            @Query("page")page:Int
    ): MovieApiResponse

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey : String

    ): GenreApiResponse


}

object MovieApi {
    val retrofitService : MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}