package com.example.moviedataapp.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedataapp.network.APIKEY
import com.example.moviedataapp.network.IMDbApi
import com.example.moviedataapp.network.MovieResult
import kotlinx.coroutines.launch

enum class IMDbApiStatus { LOADING, ERROR, DONE }

private const val TAG = "MoviesViewModel"

class MoviesViewModel(application: Application, apiString: String) :
		AndroidViewModel(application) {

	private val _status = MutableLiveData<IMDbApiStatus>()
	val status: LiveData<IMDbApiStatus> get() = _status

	private val _movies = MutableLiveData<List<MovieResult.Movie>>()
	val movies: LiveData<List<MovieResult.Movie>> get() = _movies

	private val _navigateToSelectedMovie = MutableLiveData<Long?>()
	val navigateToSelectedMovie: LiveData<Long?> get() = _navigateToSelectedMovie

	private val _selectedApiString = MutableLiveData<String>()
	val selectedApiString: LiveData<String> get() = _selectedApiString

	init {
		_selectedApiString.value = apiString
		getMovies()
	}

	private fun getMovies() {
		viewModelScope.launch {
			_status.value = IMDbApiStatus.LOADING
			try {
				val res = IMDbApi.retrofitService.getMovies(selectedApiString.value!!, APIKEY, "US")
				_movies.value = res.results
				_status.value = IMDbApiStatus.DONE
			} catch (e: Exception) {
				_status.value = IMDbApiStatus.ERROR
				_movies.value = ArrayList()
			}
		}
	}

	fun displayMovieDetails(movieId: Long) {
		_navigateToSelectedMovie.value = movieId
	}

	fun displayMovieDetailsComplete() {
		_navigateToSelectedMovie.value = null
	}

	fun updateMovies(changedApiString: String) {
		_selectedApiString.value = changedApiString
		getMovies()
	}
}