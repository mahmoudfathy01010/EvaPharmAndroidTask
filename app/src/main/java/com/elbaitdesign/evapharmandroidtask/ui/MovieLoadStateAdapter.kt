package com.elbaitdesign.evapharmandroidtask.ui

import android.content.Context
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class MovieLoadStateAdapter(private val retry:()->Unit, val context:Context):LoadStateAdapter<MovieLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState,context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.create(parent, retry, context)

    }
}