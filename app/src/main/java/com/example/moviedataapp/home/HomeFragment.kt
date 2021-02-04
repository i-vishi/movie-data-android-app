package com.example.moviedataapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.FragmentHomeBinding
import com.example.moviedataapp.network.TopicsData

class HomeFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val binding: FragmentHomeBinding = DataBindingUtil
				.inflate(inflater, R.layout.fragment_home, container, false)
		val dataSource = TopicsData()
		val viewModelFactory = HomeViewModelFactory(dataSource)

		val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
		binding.viewModel = homeViewModel
		binding.lifecycleOwner = this

		val adapter = this.context?.let { HomeAdapter(it, dataSource.loadTopics()) }
		binding.topicList.adapter = adapter

		return binding.root
	}

}