package com.example.moviedataapp.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.moviedataapp.R
import com.example.moviedataapp.network.IMDbApi
import com.example.moviedataapp.network.MovieDetail
import kotlinx.coroutines.launch
import okhttp3.internal.trimSubstring
import java.lang.Exception

enum class IMDbApiStatus { LOADING, ERROR, DONE }

private const val TAG = "MoviesViewModel"

class MoviesViewModel(application: Application, apiString: String) :
    AndroidViewModel(application) {

    private val _status = MutableLiveData<IMDbApiStatus>()
    val status: LiveData<IMDbApiStatus> get() = _status

    private val _movies = MutableLiveData<List<MovieDetail>>()
    val movies: LiveData<List<MovieDetail>> get() = _movies

    private val _navigateToSelectedMovie = MutableLiveData<MovieDetail?>()
    val navigateToSelectedMovie: LiveData<MovieDetail?> get() = _navigateToSelectedMovie

    private val movieList = mutableListOf<MovieDetail>()

    private val _selectedApiString = MutableLiveData<String>()
    val selectedApiString: LiveData<String> get() = _selectedApiString

    val app = application

    init {
        _selectedApiString.value = apiString
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _status.value = IMDbApiStatus.LOADING
            try {
                val res = when (_selectedApiString.value) {

                    app.applicationContext.getString(R.string.apiTopRated) -> {
                        val resTopRated = IMDbApi.retrofitService.getTopRatedMovies()
                        resTopRated[0]
                    }
                    app.applicationContext.getString(R.string.apiComingSoon) -> IMDbApi.retrofitService.getComingSoonMovies()
                    app.applicationContext.getString(R.string.apiTrending) -> IMDbApi.retrofitService.getTrendingMovies()
                    else -> mutableListOf()
                }

                // add response to movies
                val res2 = res.subList(0, 5)
                for (item in res2) {
                    val tconst = item.trimSubstring(7, item.length - 1)
                    val m = IMDbApi.retrofitService.getMovieDetails(tconst)
                    Log.d(TAG, m.toString())
                    movieList.add(m)
                }
                _movies.value = movieList
                _status.value = IMDbApiStatus.DONE
                Log.d(TAG, status.value.toString())
            } catch (e: Exception) {
                _status.value = IMDbApiStatus.ERROR
                _movies.value = ArrayList()
                Log.d(TAG, status.value.toString() + " ${e.message}")
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

    fun displayMovieDetails(movie: MovieDetail) {
        _navigateToSelectedMovie.value = movie
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