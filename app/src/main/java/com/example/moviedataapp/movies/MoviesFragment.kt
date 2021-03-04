package com.example.moviedataapp.movies

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.FragmentMoviesBinding
import com.example.moviedataapp.home.AboutDialogFragment
import com.example.moviedataapp.network.MovieResult
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView


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
		apiGetString = getString(R.string.apiTrending)

		binding.topAppBar.setNavigationOnClickListener {
			binding.drawerLayout.open()
		}

		binding.navigationView.setNavigationItemSelectedListener { menuItem ->
			if (menuItem.groupId == R.id.topicGroup) {
				viewModel.updateMovies(
						when (menuItem.itemId) {
							R.id.now_playing_menu -> getString(R.string.apiNowPlaying)
							R.id.top_rated_menu -> getString(R.string.apiTopRated)
							R.id.upcoming_menu -> getString(R.string.apiComingSoon)
							else -> getString(R.string.apiTrending)
						}
				)
				menuItem.isChecked = true
			} else {
				showAbout()
			}
			binding.drawerLayout.close()
			true
		}

//		val activity = activity as AppCompatActivity
//		activity.supportActionBar?.title = getScreenTitle()

//		setHasOptionsMenu(true)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.lifecycleOwner = this
		binding.viewModel = viewModel

		viewModel.selectedApiString.observe(viewLifecycleOwner, {
			binding.topicName.text = when (it) {
				getString(R.string.apiComingSoon) -> getString(R.string.comingSoon)
				getString(R.string.apiTopRated) -> getString(R.string.topRated)
				getString(R.string.apiNowPlaying) -> getString(R.string.nowPlaying)
				else -> getString(R.string.trending)
			}
		})

		val adapter = PhotoGridAdapter(requireContext())
		adapter.onClickListener = object : PhotoGridAdapter.OnClickListener {
			override fun onClick(movieData: MovieResult.Movie, cardView: MaterialCardView, textView: MaterialTextView) {
				viewModel.displayMovieDetails(movieData.id)
				val extras = FragmentNavigatorExtras(cardView to movieData.id.toString())
				val action = MoviesFragmentDirections
						.actionMoviesFragmentToMovieDetailFragment(movieData.id, cardView.cardBackgroundColor.defaultColor, textView.textColors.defaultColor)
				findNavController()
						.navigate(action, extras)
				viewModel.displayMovieDetailsComplete()
			}
		}

		binding.moviesPhotoGrid.adapter = adapter
	}

//	override fun onCreate(savedInstanceState: Bundle?) {
//		super.onCreate(savedInstanceState)
//		val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
//			override fun handleOnBackPressed() {
//				this@MoviesFragment.findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToHomeFragment())
//			}
//		}
//		requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//
//	}

//	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//		inflater.inflate(R.menu.topic_menu, menu)
//		super.onCreateOptionsMenu(menu, inflater)
//	}
//
//	override fun onOptionsItemSelected(item: MenuItem): Boolean {
//		Log.d("topicOption", item.title.toString())
//
//		viewModel.updateMovies(
//				when (item.itemId) {
//					R.id.now_playing_menu -> getString(R.string.apiNowPlaying)
//					R.id.top_rated_menu -> getString(R.string.apiTopRated)
//					R.id.upcoming_menu -> getString(R.string.apiComingSoon)
//					else -> getString(R.string.apiTrending)
//				}
//		)
//
//		return true
//	}

//	private fun getScreenTitle(): String {
//		return when (apiGetString) {
//			context?.getString(R.string.apiTrending) -> requireContext().getString(R.string.trending)
//			context?.getString(R.string.apiComingSoon) -> requireContext().getString(R.string.comingSoon)
//			context?.getString(R.string.apiTopRated) -> requireContext().getString(R.string.topRated)
//			else -> requireContext().getString(R.string.app_name)
//		}
//	}

	private fun showAbout() {
		val aboutFragment = AboutDialogFragment()
		aboutFragment.show(childFragmentManager, AboutDialogFragment.TAG)
	}
}