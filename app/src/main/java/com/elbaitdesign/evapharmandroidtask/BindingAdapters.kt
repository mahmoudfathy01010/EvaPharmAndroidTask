package com.elbaitdesign.evapharmandroidtask

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.elbaitdesign.evapharmandroidtask.api.IMAGE_BASE_URL

@BindingAdapter("setImage")
fun ImageView.setImage(url:String?){
    Glide.with(context)
        .load(IMAGE_BASE_URL+url)
        .centerCrop()
        .error(R.drawable.image_error)
        .placeholder(R.drawable.image_place_holder)
        .into(this)
}

@BindingAdapter("setVoteAverage")
fun TextView.setVoteAverage(voteAverage:Double){
    text = Html.fromHtml(String.format(context.getString(R.string.vote_average),voteAverage))
}

@BindingAdapter("setCount")
fun TextView.setCount(count:Int){
    text = Html.fromHtml(String.format(context.getString(R.string.count),count))
}

@BindingAdapter("isFavourite")
fun TextView.isFavourite(isFavourite: Boolean){
        isVisible=!isFavourite
}
