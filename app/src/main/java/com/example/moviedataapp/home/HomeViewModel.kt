package com.example.moviedataapp.home

import androidx.lifecycle.ViewModel
import com.example.moviedataapp.network.TopicsData

class HomeViewModel(val database: TopicsData): ViewModel() {
	val topics = database.loadTopics()
}