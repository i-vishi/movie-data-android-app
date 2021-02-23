package com.example.moviedataapp.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviedataapp.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {

    private lateinit var apiGetString: String

    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMoviesBinding.inflate(inflater)

        val arguments = MoviesFragmentArgs.fromBundle(requireArguments())

        apiGetString = arguments.selectedTopicAPI

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.moviesPhotoGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
            if(null != it) {
                this.findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        return binding.root
    }
}