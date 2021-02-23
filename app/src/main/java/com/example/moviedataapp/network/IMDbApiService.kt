package com.example.moviedataapp.network

import com.example.moviedataapp.BuildConfig
import com.example.moviedataapp.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://imdb8.p.rapidapi.com/title/"
private const val APIKEY = BuildConfig.API_KEY

private val moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()

private val okHttpClient = OkHttpClient.Builder().apply {
	addInterceptor(
			Interceptor { chain ->
				val builder = chain.request().newBuilder()
				builder.header("x-rapidapi-key", APIKEY)
				builder.header("x-rapidapi-host", "imdb8.p.rapidapi.com")
				return@Interceptor chain.proceed(builder.build())
			}
	)
}.build()

private val retrofit = Retrofit.Builder()
		.client(okHttpClient)
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.baseUrl(BASE_URL)
		.build()

interface IMDbApiService {
	@GET("get-most-popular-movies")
	suspend fun getTrendingMovies(): List<String>

	@GET("get-top-rated-movies")
	suspend fun getTopRatedMovies(): List<String>

	@GET("get-coming-soon-movies")
	suspend fun getComingSoonMovies(): List<String>

	@GET("get-details")
	suspend fun getMovieDetails(@Query("tconst") tconst: String): MovieDetail
}

object IMDbApi {
	val retrofitService: IMDbApiService by lazy {
		retrofit.create(IMDbApiService::class.java)
	}
}


// all topics to be shown on Home
class TopicsData {
	fun loadTopics() : List<Topic> {
		return listOf(
				Topic(R.string.trending, R.string.apiTrending),
				Topic(R.string.topRated, R.string.apiTopRated),
				Topic(R.string.comingSoon, R.string.apiComingSoon),
		)
	}
}