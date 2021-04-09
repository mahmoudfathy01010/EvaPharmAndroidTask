package com.elbaitdesign.evapharmandroidtask.ui.top_rated

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elbaitdesign.evapharmandroidtask.api.MovieApiName
import com.elbaitdesign.evapharmandroidtask.model.Movie
import com.elbaitdesign.evapharmandroidtask.ui.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TopRatedFragment : BaseFragment() {
     private suspend fun setApiName(apiKeyQuery: String) {
        viewModel.getMovies( apiKeyQuery, MovieApiName.TOP_RATED,null)
            .collect {
                adapter.submitData(it)
            }
    }

    override fun handleSearchAvailability() {
        binding.searchLayout.visibility= View.GONE
    }

    override fun navigate(movie: Movie) {
        findNavController().navigate(TopRatedFragmentDirections.actionTopRatedFragmentToMovieDetails(movie))
    }

    override fun getData(apiKeyQuery: String) {
        job?.cancel()
        job = lifecycleScope.launch {
            setApiName(apiKeyQuery)
        }
    }


}