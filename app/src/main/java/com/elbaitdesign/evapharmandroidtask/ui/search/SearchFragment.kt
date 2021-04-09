package com.elbaitdesign.evapharmandroidtask.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elbaitdesign.evapharmandroidtask.afterTextChanged
import com.elbaitdesign.evapharmandroidtask.api.API_KEY
import com.elbaitdesign.evapharmandroidtask.api.MovieApiName
import com.elbaitdesign.evapharmandroidtask.model.Movie
import com.elbaitdesign.evapharmandroidtask.ui.BaseFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class SearchFragment : BaseFragment() {
     var query:String? = null
    private suspend fun setApiName(apiKeyQuery: String, searchText: String?) {
        if (searchText != null && searchText != "") {
            viewModel.getMovies(apiKeyQuery, MovieApiName.SEARCH, searchText)
                .collect {
                    adapter.submitData(it)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null){
            query = savedInstanceState.getString(LAST_SEARCH_QUERY)
            if(query!=null && query?.isNotEmpty()!!)
            searchFromApi(query!!)
        }

    }

    override fun onResume() {
        super.onResume()
        if(query!=null && query?.isNotEmpty()!!)
            searchFromApi(query!!)
    }

    override fun handleSearchAvailability() {
        binding.searchLayout.visibility = View.VISIBLE
    }

    override fun navigate(movie: Movie){
       findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMovieDetails(movie))
    }

    override fun getData(apiKeyQuery: String) {
        initSearch()
    }


    private fun initSearch() {
        binding.searchMovie.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                getSearchQuery()
                true
            } else {
                false
            }
        }
        binding.searchMovie.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                getSearchQuery()
                true
            } else {
                false
            }
        }
        bindSearchButtonWithSearchEditText()
        binding.searchButton.setOnClickListener {
            getSearchQuery()
        }
    }

    private fun getSearchQuery() {
        binding.searchMovie.text.trim().let { text ->
            searchFromApi(text)
            query=text.toString()
        }
    }

    private  fun searchFromApi(text: CharSequence) {
        if (text.isNotEmpty()) {
            job?.cancel()
            job = lifecycleScope.launch {
                setApiName(API_KEY, text.toString())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchMovie.text.trim().toString())
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
    }

    private fun bindSearchButtonWithSearchEditText(){
        binding.searchMovie.afterTextChanged {
            binding.searchButton.isEnabled=it.isNotEmpty()
        }
    }
}