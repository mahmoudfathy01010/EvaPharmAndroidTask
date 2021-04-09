package com.elbaitdesign.evapharmandroidtask.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class Movie (
        @PrimaryKey @SerializedName("id")val id:Long,
        @SerializedName("poster_path")val image:String?,
        @SerializedName("title")val title:String,
        @SerializedName("overview")val overView:String,
        @SerializedName("vote_average")val voteAverage:Double,
        @SerializedName("vote_count")val voteCount:Int,
        @SerializedName("genre_ids")val genreIds :List<Int> = emptyList(),
        var isFavourite:Boolean
    ) : Parcelable