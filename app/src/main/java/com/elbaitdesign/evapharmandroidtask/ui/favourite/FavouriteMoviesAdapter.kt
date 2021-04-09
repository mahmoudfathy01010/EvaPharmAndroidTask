package com.elbaitdesign.evapharmandroidtask.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elbaitdesign.evapharmandroidtask.databinding.RowMovieBinding
import com.elbaitdesign.evapharmandroidtask.model.Movie
import com.elbaitdesign.evapharmandroidtask.ui.MovieDiffCallBack
import com.elbaitdesign.evapharmandroidtask.ui.MovieListener

class FavouriteMoviesAdapter(private val listener: MovieListener) : ListAdapter<Movie, FavouriteMoviesAdapter.ViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item,listener)
        }
    }

    class ViewHolder(private val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie, listener: MovieListener) {
            binding.movie=item
            binding.addToFavouriteListener=listener
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RowMovieBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    }

