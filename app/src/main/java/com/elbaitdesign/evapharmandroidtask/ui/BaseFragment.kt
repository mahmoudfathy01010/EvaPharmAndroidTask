package com.elbaitdesign.evapharmandroidtask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.elbaitdesign.evapharmandroidtask.Injection
import com.elbaitdesign.evapharmandroidtask.R
import com.elbaitdesign.evapharmandroidtask.api.API_KEY
import com.elbaitdesign.evapharmandroidtask.databinding.FragmentBaseBinding
import com.elbaitdesign.evapharmandroidtask.db.MovieDao
import com.elbaitdesign.evapharmandroidtask.db.MovieDataBase
import com.elbaitdesign.evapharmandroidtask.model.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {
    lateinit var binding: FragmentBaseBinding
    lateinit var viewModel: MovieViewModel
    var job: Job? = null
    lateinit var adapter :MovieAdapter
    lateinit var datBaseSource:MovieDao


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_base,container,false)
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(MovieViewModel::class.java)
        handleSearchAvailability()
        setRecyclerItemDecoration()
        initAdapter()
        getData(API_KEY)
        binding.retryButton.setOnClickListener { adapter.retry() }
        datBaseSource = MovieDataBase.getInstance(requireContext()).movieDao
        return binding.root
    }

    private fun setRecyclerItemDecoration() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.recycleView.addItemDecoration(decoration)
    }

    private fun initAdapter() {
        adapter = MovieAdapter(MovieListener {
            job?.cancel()
            job = lifecycleScope.launch {
                it.isFavourite=true
                viewModel.insertFavouriteMovie(it,datBaseSource)
                Snackbar.make(binding.root,getString(R.string.movie_added_to_favourite),Snackbar.LENGTH_SHORT).show()
            }
        }, MovieListener {
            navigate(it)
        }
        )
        binding.recycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = MovieLoadStateAdapter({adapter.retry()},requireContext()),
            header = MovieLoadStateAdapter({adapter.retry()},requireContext())
        )
        adapter.addLoadStateListener  { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)
            binding.recycleView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.emptyLayout.isVisible = loadState.source.refresh is LoadState.Error
            binding.loading.isVisible = loadState.source.refresh is LoadState.Loading
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        }
    }

    abstract fun navigate(movie: Movie)

    private fun showEmptyList(show: Boolean) {
        if(binding.searchMovie.text.isNotEmpty()) {
            if (show) {
                binding.emptyList.visibility = View.VISIBLE
                binding.recycleView.visibility = View.GONE
            } else {
                binding.emptyList.visibility = View.GONE
                binding.recycleView.visibility = View.VISIBLE
                binding.typeToSearch.visibility=View.GONE
            }
        }
        else {
            if (show) {
                binding.typeToSearch.visibility = View.VISIBLE
                binding.recycleView.visibility = View.GONE
            } else {
                binding.typeToSearch.visibility = View.GONE
                binding.recycleView.visibility = View.VISIBLE
            }

        }
    }

    abstract fun getData(apiKeyQuery:String)
    abstract fun handleSearchAvailability()


}