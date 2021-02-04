package com.example.moviedataapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedataapp.network.TopicsData
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val database: TopicsData) : ViewModelProvider.Factory {

	@Suppress("unchecked_cast")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
			return HomeViewModel(database = database) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}