package com.example.moviedataapp.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

//@Parcelize
//data class Movie(
//    val id: String,
//    val imageUrl: String,
//    @Json(name = "@type") val type: String,
//    val runningTimeInMinutes: Int,
//    val title: String,
//    val titleType: String,
//    val year: Int
//
//) : Parcelable {
//
//
////    Response Example
////
////    "@type":"imdb.api.title.title"
////    "id":"/title/tt0111161/"
////    "image":{4 items
////            "height":1000
////        "id":"/title/tt0111161/images/rm10105600"
////        "url":"https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"
////        "width":674
////    }
////    "runningTimeInMinutes":142
////    "title":"The Shawshank Redemption"
////    "titleType":"movie"
////    "year":1994
//
//}

@Parcelize
data class Movie(
    val id: Int,
    var title: String
) : Parcelable {}


@Parcelize
data class MovieDetail(
    val id: String,
    val image: Image,
    val runningTimeInMinutes: Int = 0,
    val title: String,
    val titleType: String,
    @Json(name = "@type") val type: String,
    val year: Int = 2021
) : Parcelable{

@Parcelize
    data class Image(
        val height: Int = 180,
        val id: String,
        val url: String,
        val width: Int = 500
    ) : Parcelable {}
}