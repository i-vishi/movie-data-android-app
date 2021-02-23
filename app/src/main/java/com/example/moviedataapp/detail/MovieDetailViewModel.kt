package com.example.moviedataapp.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.moviedataapp.R
import com.example.moviedataapp.network.MovieDetail

class MovieDetailViewModel(movieData: MovieDetail, application: Application) :
    AndroidViewModel(application) {

    private val _selectedMovie = MutableLiveData<MovieDetail>()
    val selectedMovie: LiveData<MovieDetail> get() = _selectedMovie

    init {
        _selectedMovie.value = movieData
    }

    val getReleaseYear = Transformations.map(selectedMovie) {
        application.applicationContext.getString(R.string.display_release_year, it.year)
    }

    val getRunningTime = Transformations.map(selectedMovie) {
        when (it.runningTimeInMinutes) {
            0 -> "Running Time: NA"
            else -> {
                application.applicationContext.getString(
                    R.string.display_running_time,
                    it.runningTimeInMinutes
                )
            }
        }

    }
}