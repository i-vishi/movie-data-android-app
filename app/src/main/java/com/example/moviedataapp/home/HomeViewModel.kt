package com.example.moviedataapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedataapp.network.Topic
import com.example.moviedataapp.network.TopicsData

class HomeViewModel(val database: TopicsData) : ViewModel() {
    val topics = database.loadTopics()

//	private val _movies = MutableLiveData<List<Movie>>

    private val _navigateToSelectedTopic = MutableLiveData<String>()
    val navigateToSelectedTopic: LiveData<String> get() = _navigateToSelectedTopic

	fun displayMoviesFromTopic(apiText: String) {
        _navigateToSelectedTopic.value = apiText
    }
}