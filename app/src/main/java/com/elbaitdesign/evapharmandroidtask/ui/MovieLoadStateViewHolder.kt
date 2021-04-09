package com.elbaitdesign.evapharmandroidtask.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.elbaitdesign.evapharmandroidtask.R
import com.elbaitdesign.evapharmandroidtask.databinding.FooterMovieStateBinding

class MovieLoadStateViewHolder(
    private val binding : FooterMovieStateBinding,
    retry: ()->Unit
):RecyclerView.ViewHolder(binding.root)
{
    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState:LoadState,context:Context){
        if(loadState is LoadState.Error){
            binding.errorMsg.text= context.getString(R.string.something_went_wrong)
        }
        binding.progressLottie.isVisible=loadState is LoadState.Loading
        binding.retryButton.isVisible=loadState is LoadState.Error
        binding.errorMsg.isVisible=loadState is LoadState.Error
    }

    companion object {
        fun create(
                parent: ViewGroup,
                retry: () -> Unit,
                context: Context
        ): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.footer_movie_state, parent, false)
            val binding = FooterMovieStateBinding.bind(view)
            return MovieLoadStateViewHolder(binding, retry)
        }
    }
}