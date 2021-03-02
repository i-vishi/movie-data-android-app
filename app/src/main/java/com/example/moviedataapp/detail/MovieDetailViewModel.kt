package com.example.moviedataapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.moviedataapp.R
import com.example.moviedataapp.movies.IMDbApiStatus
import com.example.moviedataapp.network.APIKEY
import com.example.moviedataapp.network.IMDbApi
import com.example.moviedataapp.network.MovieDetail
import com.example.moviedataapp.network.MovieResult
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(movieDataId: Long, application: Application) :
		AndroidViewModel(application) {

	private val _selectedMovieId = MutableLiveData<Long>()
	val selectedMovieId: LiveData<Long> get() = _selectedMovieId

	private val _status = MutableLiveData<IMDbApiStatus>()
	val status: LiveData<IMDbApiStatus> get() = _status

	private val _movieData = MutableLiveData<MovieDetail?>()
	val movieData: LiveData<MovieDetail?> get() = _movieData

	init {
		_selectedMovieId.value = movieDataId
		getMovieData()
	}

	private fun getMovieData() {
		viewModelScope.launch {
			_status.value = IMDbApiStatus.LOADING
			try {
				Log.d(TAG, "id is ${_selectedMovieId.value.toString()}")
				_movieData.value = IMDbApi.retrofitService.getMovieDetails(_selectedMovieId.value!!, APIKEY)
				Log.d(TAG, _movieData.value.toString())
				_status.value = IMDbApiStatus.DONE
			} catch (e: Exception) {
				_status.value = IMDbApiStatus.ERROR
				_movieData.value = null
				Log.d(TAG, status.value.toString() + "  $e")
			}
		}
	}

	val getReleaseDate = Transformations.map(movieData) {
		application.applicationContext.getString(R.string.display_release_year, it?.releaseDate)
	}

	val getRunningTime = Transformations.map(movieData) {
		when (it?.runtime) {
			0L -> "Running Time: NA"
			else -> {
				application.applicationContext.getString(
						R.string.display_running_time,
						it?.runtime
				)
			}
		}

	}
}