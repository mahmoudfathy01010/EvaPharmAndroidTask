package com.elbaitdesign.evapharmandroidtask.ui.details

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.elbaitdesign.evapharmandroidtask.Injection
import com.elbaitdesign.evapharmandroidtask.R
import com.elbaitdesign.evapharmandroidtask.api.API_KEY
import com.elbaitdesign.evapharmandroidtask.databinding.FragmentMovieDetailsBinding
import com.elbaitdesign.evapharmandroidtask.db.MovieDao
import com.elbaitdesign.evapharmandroidtask.db.MovieDataBase
import com.elbaitdesign.evapharmandroidtask.model.Genre
import com.elbaitdesign.evapharmandroidtask.model.Movie
import com.elbaitdesign.evapharmandroidtask.ui.MovieListener
import com.elbaitdesign.evapharmandroidtask.ui.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MovieDetails : Fragment() {
    lateinit var binding:FragmentMovieDetailsBinding
    lateinit var viewModel: MovieViewModel
    lateinit var datBaseSource: MovieDao
    var job: Job? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(MovieViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_details,container,false)
        binding.lifecycleOwner=this
        getData()
        datBaseSource = MovieDataBase.getInstance(requireContext()).movieDao
        val movie = getMovieFromArgs()
        viewModel.genres.observe(viewLifecycleOwner, Observer { totalGenres->
            val movieGenres = movie?.genreIds?.map { movieGenreId->
                totalGenres.filter{ checkGenre(movieGenreId,it.id)}[0]
            }
            binding.genres.text=buildGenreText(movieGenres)
        })

        binding.addToFavouriteListener= MovieListener {
            job?.cancel()
            job = lifecycleScope.launch {
                it.isFavourite = true
                viewModel.insertFavouriteMovie(it, datBaseSource)
                Snackbar.make(binding.root, getString(R.string.movie_added_to_favourite), Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun getMovieFromArgs(): Movie? {
        val args = MovieDetailsArgs.fromBundle(requireArguments())
        binding.movie= args.movie
        return args.movie
    }

    private fun getData() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getGenres(API_KEY)
        }
    }

    private fun checkGenre(movieId:Int, comparedId:Int):Boolean{
        return movieId==comparedId
    }

    private fun buildGenreText(genreList: List<Genre>?): Spanned? {
        return Html.fromHtml(String.format(getString(R.string.genre),genreList?.joinToString { it.name }))
    }

}