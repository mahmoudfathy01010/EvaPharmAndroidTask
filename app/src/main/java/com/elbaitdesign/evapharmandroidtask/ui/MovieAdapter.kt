package com.elbaitdesign.evapharmandroidtask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elbaitdesign.evapharmandroidtask.databinding.RowMovieBinding
import com.elbaitdesign.evapharmandroidtask.model.Movie

class MovieAdapter(private val addToFavouriteListener: MovieListener,
                   private val openMovieDetailsListener: MovieListener

) : PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item,addToFavouriteListener,openMovieDetailsListener)
        }
    }

    class ViewHolder(private val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie, addToFavouriteListener: MovieListener, openMovieDetailsListener: MovieListener) {
            binding.movie=item
            binding.addToFavouriteListener=addToFavouriteListener
            binding.openMovieDetailsListener=openMovieDetailsListener
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



class MovieDiffCallBack:DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =oldItem.id==newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem==newItem
}

class MovieListener(private val clickListener : (movie: Movie)->Unit){
    fun onClick(item: Movie)= clickListener(item)
}