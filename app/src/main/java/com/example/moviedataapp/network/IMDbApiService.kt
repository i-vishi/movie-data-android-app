package com.example.moviedataapp.network

import com.example.moviedataapp.BuildConfig
import com.example.moviedataapp.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val APIKEY = BuildConfig.API_KEY

private val moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()

private val retrofit = Retrofit.Builder()
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.baseUrl(BASE_URL)
		.build()

interface IMDbApiService {
	@GET("{criteria}")
	suspend fun getMovies(@Path(value = "criteria", encoded = true) criteria: String, @Query("api_key") api_key: String, @Query("region") region: String): MovieResult

	@GET("{movieId}")
	suspend fun getMovieDetails(@Path(value = "movieId", encoded = true) movieId: Int, @Query("api_key") api_key: String): MovieDetail
}

object IMDbApi {
	val retrofitService: IMDbApiService by lazy {
		retrofit.create(IMDbApiService::class.java)
	}
}


// all topics to be shown on Home
class TopicsData {
	fun loadTopics(): List<Topic> {
		return listOf(
				Topic(R.string.trending, R.string.apiTrending),
				Topic(R.string.topRated, R.string.apiTopRated),
				Topic(R.string.comingSoon, R.string.apiComingSoon),
				Topic(R.string.nowPlaying, R.string.apiNowPlaying),
		)
	}
}