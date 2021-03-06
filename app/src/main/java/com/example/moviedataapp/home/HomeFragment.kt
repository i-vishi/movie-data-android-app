package com.example.moviedataapp.home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.FragmentHomeBinding
import com.example.moviedataapp.network.TopicsData

class HomeFragment : Fragment() {

	private lateinit var binding: FragmentHomeBinding

	override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
		binding = DataBindingUtil
				.inflate(inflater, R.layout.fragment_home, container, false)
		val dataSource = TopicsData()
		val viewModelFactory = HomeViewModelFactory(dataSource)

		val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
		binding.viewModel = homeViewModel
		binding.lifecycleOwner = this

		val adapter = this.context?.let { topic ->
			HomeAdapter(topic, dataSource.loadTopics(), HomeAdapter.OnClickListener {
                homeViewModel.displayMoviesFromTopic(it)
            })
		}

		binding.topicList.adapter = adapter

//		homeViewModel.navigateToSelectedTopic.observe(viewLifecycleOwner, {
//            if (null != it) {
//                this.findNavController()
//                        .navigate(HomeFragmentDirections.actionHomeFragmentToMoviesFragment(it))
//            }
//        })

		setHasOptionsMenu(true)

		return binding.root
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.overflow_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.about) {
			showAbout()
		}
		return true
	}

	private fun showAbout() {
		val aboutFragment = AboutDialogFragment()
		aboutFragment.show(childFragmentManager, AboutDialogFragment.TAG)
	}
}