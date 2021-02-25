package com.example.moviedataapp.movies

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.FragmentMoviesBinding
import kotlin.concurrent.fixedRateTimer

class MoviesFragment : Fragment() {


    class MovieViewModelFactory(
        private val application: Application,
        private val apiString: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
                return MoviesViewModel(application, apiString) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private lateinit var apiGetString: String

    private val viewModel: MoviesViewModel by viewModels {
        MovieViewModelFactory(requireNotNull(activity).application, apiGetString)
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

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.title = getScreenTitle()


        binding.moviesPhotoGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it.id)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController()
                    .navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        return binding.root
    }



    private fun getScreenTitle(): String {
        return when(apiGetString) {
            context?.getString(R.string.apiTrending) -> requireContext().getString(R.string.trending)
            context?.getString(R.string.apiComingSoon) -> requireContext().getString(R.string.comingSoon)
            context?.getString(R.string.apiTopRated) -> requireContext().getString(R.string.topRated)
            else -> requireContext().getString(R.string.app_name)
        }
    }
}