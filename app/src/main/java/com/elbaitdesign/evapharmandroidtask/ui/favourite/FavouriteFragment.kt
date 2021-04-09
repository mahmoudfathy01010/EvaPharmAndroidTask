package com.elbaitdesign.evapharmandroidtask.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.elbaitdesign.evapharmandroidtask.Injection
import com.elbaitdesign.evapharmandroidtask.R
import com.elbaitdesign.evapharmandroidtask.api.API_KEY
import com.elbaitdesign.evapharmandroidtask.databinding.FragmentFavouriteBinding
import com.elbaitdesign.evapharmandroidtask.db.MovieDao
import com.elbaitdesign.evapharmandroidtask.db.MovieDataBase
import com.elbaitdesign.evapharmandroidtask.model.Movie
import com.elbaitdesign.evapharmandroidtask.ui.MovieListener
import com.elbaitdesign.evapharmandroidtask.ui.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {
    lateinit var binding: FragmentFavouriteBinding
    lateinit var viewModel: MovieViewModel
    lateinit var adapter : FavouriteMoviesAdapter
    var job: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favourite,container,false)
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(MovieViewModel::class.java)
        setRecyclerItemDecoration()
        initAdapter()
        val dataSource = MovieDataBase.getInstance(requireContext()).movieDao
        viewModel.getFavouriteMovies(dataSource)
        viewModel.favouriteMovies.observe(viewLifecycleOwner, Observer {
            updateUI(it)
            adapter.submitList(it)
        })

        clearList(dataSource)

        return binding.root
    }

    private fun updateUI(it: List<Movie>) {
        binding.noFavourite.isVisible = it.isEmpty()
        binding.clearList.isEnabled = it.isNotEmpty()
    }

    private fun clearList(dataSource: MovieDao) {
        binding.clearList.setOnClickListener {
            job?.cancel()
            job = lifecycleScope.launch {
                viewModel.deleteFavouriteList(dataSource)
                Snackbar.make(binding.root, getString(R.string.favourite_list_clear), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        adapter = FavouriteMoviesAdapter(MovieListener {
        })
        binding.recycleView.adapter=adapter
    }

    private fun setRecyclerItemDecoration() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.recycleView.addItemDecoration(decoration)
    }


}