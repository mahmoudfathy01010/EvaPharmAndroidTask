package com.elbaitdesign.evapharmandroidtask.model

import com.google.gson.annotations.SerializedName

data class GenreApiResponse(
        @SerializedName("genres")val genres:List<Genre> = emptyList()
)
data class Genre(@SerializedName("id")val id:Int,
                 @SerializedName("name")val name:String
)