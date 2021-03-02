package com.example.moviedataapp.movies

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.FragmentMoviesBinding
import kotlinx.android.synthetic.main.grid_view_item.view.*
import kotlinx.android.synthetic.main.movie_detail_fragment.*


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
	private lateinit var binding: FragmentMoviesBinding

	private val viewModel: MoviesViewModel by viewModels {
		MovieViewModelFactory(requireNotNull(activity).application, apiGetString)
	}

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	): View {

		binding = FragmentMoviesBinding.inflate(inflater)
		val arguments = MoviesFragmentArgs.fromBundle(requireArguments())
		apiGetString = arguments.selectedTopicAPI

		val activity = activity as AppCompatActivity
		activity.supportActionBar?.title = getScreenTitle()

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.lifecycleOwner = this
		binding.viewModel = viewModel

		binding.moviesPhotoGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
			viewModel.displayMovieDetails(it.id)
		}, requireContext())
		viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
			if (null != it) {
				val extras = FragmentNavigatorExtras(binding.moviesPhotoGrid.album_card to it.toString())
				findNavController()
						.navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it), extras)
				viewModel.displayMovieDetailsComplete()
			}
		})
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
			override fun handleOnBackPressed() {
				this@MoviesFragment.findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToHomeFragment())
			}
		}
		requireActivity().onBackPressedDispatcher.addCallback(this, callback)

	}


	private fun getScreenTitle(): String {
		return when (apiGetString) {
			context?.getString(R.string.apiTrending) -> requireContext().getString(R.string.trending)
			context?.getString(R.string.apiComingSoon) -> requireContext().getString(R.string.comingSoon)
			context?.getString(R.string.apiTopRated) -> requireContext().getString(R.string.topRated)
			else -> requireContext().getString(R.string.app_name)
		}
	}
}