package com.example.moviedataapp.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.MovieDetailFragmentBinding

class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity).application
        val binding = MovieDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val marsProperty = MovieDetailFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val viewModelFactory = MovieDetailViewModelFactory(marsProperty, application)

        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        return binding.root
    }

}