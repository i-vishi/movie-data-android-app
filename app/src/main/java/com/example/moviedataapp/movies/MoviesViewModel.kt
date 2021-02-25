package com.example.moviedataapp.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.moviedataapp.network.APIKEY
import com.example.moviedataapp.network.IMDbApi
import com.example.moviedataapp.network.MovieDetail
import com.example.moviedataapp.network.MovieResult
import kotlinx.coroutines.launch
import java.lang.Exception

enum class IMDbApiStatus { LOADING, ERROR, DONE }

private const val TAG = "MoviesViewModel"

class MoviesViewModel(application: Application, apiString: String) :
		AndroidViewModel(application) {

	private val _status = MutableLiveData<IMDbApiStatus>()
	val status: LiveData<IMDbApiStatus> get() = _status

	private val _movies = MutableLiveData<List<MovieResult.Movie>>()
	val movies: LiveData<List<MovieResult.Movie>> get() = _movies

	private val _navigateToSelectedMovie = MutableLiveData<Int?>()
	val navigateToSelectedMovie: LiveData<Int?> get() = _navigateToSelectedMovie

	private val movieList = mutableListOf<MovieDetail>()

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
				val res = IMDbApi.retrofitService.getMovies(_selectedApiString.value!!, APIKEY, "IN")
				Log.d(TAG, res.toString())
				_movies.value = res.results
				_status.value = IMDbApiStatus.DONE
				Log.d(TAG, status.value.toString())
			} catch (e: Exception) {
				_status.value = IMDbApiStatus.ERROR
				_movies.value = ArrayList()
				Log.d(TAG, status.value.toString() + " $e")
			}
		}
	}

//    private suspend fun getMovieDetails() {
//            var i = 0
//            for (movie in movieList) {
//                val movieDetail = IMDbApi.retrofitService.getMovieDetails(movie.id)
//                Log.d(TAG, movieDetail.toString())
//                movieList[i] = movieDetail
//                i += 1
//            }
//            _movies.value = movieList
//
//    }

	fun displayMovieDetails(movieId: Int) {
		_navigateToSelectedMovie.value = movieId
	}

	fun displayMovieDetailsComplete() {
		_navigateToSelectedMovie.value = null
	}

//	private fun getAllMoviesDetails(responseList: List<String>) {
//		viewModelScope.launch {
//			try {
//			    for (item in responseList) {
//				}
//			}
//		}
//	}
}