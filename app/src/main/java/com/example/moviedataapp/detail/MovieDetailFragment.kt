package com.example.moviedataapp.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.MovieDetailFragmentBinding
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform

class MovieDetailFragment : Fragment() {

	private lateinit var binding: MovieDetailFragmentBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		sharedElementEnterTransition = buildContainerTransform()
		sharedElementReturnTransition = buildContainerTransform()
	}

	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View {
		val application = requireNotNull(activity).application
		binding = MovieDetailFragmentBinding.inflate(inflater)
		binding.lifecycleOwner = this
		val movieId = MovieDetailFragmentArgs.fromBundle(requireArguments()).selectedMovieId
		binding.coordinator.transitionName = movieId.toString()
		val viewModelFactory = MovieDetailViewModelFactory(movieId, application)

		binding.viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)

		return binding.root
	}

	private fun buildContainerTransform() =
			MaterialContainerTransform().apply {
				drawingViewId = R.id.nav_host_fragment
				interpolator = FastOutSlowInInterpolator()
				containerColor = MaterialColors.getColor(requireActivity().findViewById(android.R.id.content), R.attr.colorSurface)
				fadeMode = MaterialContainerTransform.FADE_MODE_OUT
				duration = 300
			}
}