package com.example.moviedataapp.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class MovieResult(
        val dates: Dates?,
        val page: Int,
        val results: List<Movie>,
        @Json(name = "total_pages")
        val totalPages: Int,
        @Json(name = "total_results")
        val totalResults: Int
) : Parcelable {
	@Parcelize
	data class Dates(
            val maximum: String,
            val minimum: String
    ) : Parcelable

	@Parcelize
	data class Movie(
            val adult: Boolean,
            @Json(name = "backdrop_path")
            val backdropPath: String?,
            @Json(name = "genre_ids")
            val genreIds: List<Int>,
            val id: Int,
            @Json(name = "original_language")
            val originalLanguage: String,
            @Json(name = "original_title")
            val originalTitle: String,
            val overview: String,
            val popularity: Double,
            @Json(name = "poster_path")
            val posterPath: String?,
            @Json(name = "release_date")
            val releaseDate: String,
            val title: String,
            val video: Boolean,
            @Json(name = "vote_average")
            val voteAverage: Double,
            @Json(name = "vote_count")
            val voteCount: Int
    ) : Parcelable
}


// MovieDetail -> Response when getting detail of a movie
@Parcelize
data class MovieDetail(
        val adult: Boolean,
        @Json(name = "backdrop_path")
        val backdropPath: String?,

        @Json(name = "belongs_to_collection")
        val belongsToCollection: @RawValue Any?,
        val budget: Int,
        val genres: List<Genre>,
        val homepage: String?,
        val id: Int,
        @Json(name = "imdb_id")
        val imdbId: String?,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        val overview: String?,
        val popularity: Double,
        @Json(name = "poster_path")
        val posterPath: String?,
        @Json(name = "production_companies")
        val productionCompanies: List<ProductionCompany>,
        @Json(name = "production_countries")
        val productionCountries: List<ProductionCountry>,
        @Json(name = "release_date")
        val releaseDate: String,
        val revenue: Int,
        val runtime: Int?,
        @Json(name = "spoken_languages")
        val spokenLanguages: List<SpokenLanguage>,
        val status: String,
        val tagline: String?,
        val title: String,
        val video: Boolean,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int
) : Parcelable {
	@Parcelize
	data class Genre(
            val id: Int,
            val name: String
    ) : Parcelable

	@Parcelize
	data class ProductionCompany(
            val id: Int,
            @Json(name = "logo_path")
            val logoPath: String?,
            val name: String,
            @Json(name = "origin_country")
            val originCountry: String
    ) : Parcelable

	@Parcelize
	data class ProductionCountry(
            @Json(name = "iso_3166_1")
            val iso31661: String,
            val name: String
    ) : Parcelable

	@Parcelize
	data class SpokenLanguage(
            @Json(name = "english_name")
            val englishName: String,
            @Json(name = "iso_639_1")
            val iso6391: String,
            val name: String
    ) : Parcelable
}