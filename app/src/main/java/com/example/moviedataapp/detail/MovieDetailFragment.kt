package com.example.moviedataapp.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.MovieDetailFragmentBinding
import com.example.moviedataapp.movies.MoviesFragmentDirections

class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity).application
        val binding = MovieDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val movieId = MovieDetailFragmentArgs.fromBundle(requireArguments()).selectedMovieId
        val viewModelFactory = MovieDetailViewModelFactory(movieId, application)

        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        return binding.root
    }

}