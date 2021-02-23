package com.example.moviedataapp.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedataapp.network.IMDbApi
import com.example.moviedataapp.network.Movie
import com.example.moviedataapp.network.MovieDetail
import kotlinx.coroutines.launch
import okhttp3.internal.trimSubstring
import java.lang.Exception

enum class IMDbApiStatus { LOADING, ERROR, DONE }

private val TAG = "MoviesViewModel"

class MoviesViewModel : ViewModel() {

    private val _status = MutableLiveData<IMDbApiStatus>()
    val status: LiveData<IMDbApiStatus> get() = _status

    private val _response = MutableLiveData<List<String>>()
    val response: LiveData<List<String>>
        get() = _response

    private val _movies = MutableLiveData<List<MovieDetail>>()
    val movies: LiveData<List<MovieDetail>> get() = _movies
//
//    private val _moviesList = MutableLiveData<List<MovieDetail>>()
//    val moviesList: LiveData<List<MovieDetail>> get() = _moviesList

    private val _navigateToSelectedMovie = MutableLiveData<MovieDetail>()
    val navigateToSelectedMovie: LiveData<MovieDetail> get() = _navigateToSelectedMovie

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _status.value = IMDbApiStatus.LOADING
            try {
                val res = IMDbApi.retrofitService.getTrendingMovies()
                _response.value = IMDbApi.retrofitService.getTrendingMovies()

                // add response to movies
                val movieList = mutableListOf<MovieDetail>()
                val res2 = res.subList(0, 10)
                for (item in res2) {
                    val tconst = item.trimSubstring(7, item.length - 1)
                    val movieDetail = IMDbApi.retrofitService.getMovieDetails(tconst)
                    movieList.add(movieDetail)
                    _movies.value = movieList

                    Log.d(TAG, movieDetail.toString())
                }
                _status.value = IMDbApiStatus.DONE
                Log.d(TAG, status.value.toString())
            } catch (e: Exception) {
                _status.value = IMDbApiStatus.ERROR
                _response.value = ArrayList()
                _movies.value = ArrayList()
                Log.d(TAG, status.value.toString() + " ${e.message}")

            }
        }
    }

    fun displayMovieDetails(movie: MovieDetail) {
        _navigateToSelectedMovie.value = movie
    }

//    fun displayMovieDetailsComplete() {
//        _navigateToSelectedMovie.value = null
//    }

//	private fun getAllMoviesDetails(responseList: List<String>) {
//		viewModelScope.launch {
//			try {
//			    for (item in responseList) {
//				}
//			}
//		}
//	}
}