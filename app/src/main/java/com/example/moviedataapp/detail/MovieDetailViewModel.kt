package com.example.moviedataapp.detail

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.moviedataapp.R
import com.example.moviedataapp.movies.IMDbApiStatus
import com.example.moviedataapp.network.APIKEY
import com.example.moviedataapp.network.IMDbApi
import com.example.moviedataapp.network.MovieDetail
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

	@RequiresApi(Build.VERSION_CODES.O)
	val getReleaseDate = Transformations.map(movieData) {
		val formatter = DateTimeFormatter.ofPattern("LLLL dd, yyyy")
		val st = LocalDate.parse(it?.releaseDate).format(formatter)
		application.applicationContext.getString(R.string.display_release_year, st)
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

	val getBudget = Transformations.map(movieData) {
		when (it?.budget) {
			0L -> "NA"
			else -> application.applicationContext.getString(R.string.movie_budget_display, it?.budget)
		}
	}

	val getRevenue = Transformations.map(movieData) {
		when (it?.revenue) {
			0L -> "NA"
			else -> application.applicationContext.getString(R.string.movie_revenue_display, it?.revenue)
		}
	}

	val getGenres = Transformations.map(movieData) {
		when (it?.genres?.size) {
			0 -> "Genre: To be Declared"
			else -> it?.genres?.joinToString { genre -> genre.name }
		}
	}

	val getLanguage = Transformations.map(movieData) {
		Locale(it?.originalLanguage!!).displayLanguage
	}
}