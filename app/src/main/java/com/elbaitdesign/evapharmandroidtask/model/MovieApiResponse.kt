package com.elbaitdesign.evapharmandroidtask.model

import com.google.gson.annotations.SerializedName

data class MovieApiResponse(
    @SerializedName("results")val result: List<Movie> = emptyList()
    )