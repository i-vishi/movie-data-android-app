package com.example.moviedataapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedataapp.network.MovieDetail

class MovieDetailViewModelFactory(
    private val movieData: MovieDetail,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieData, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}