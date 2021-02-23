package com.example.moviedataapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviedataapp.home.HomeAdapter
import com.example.moviedataapp.movies.IMDbApiStatus
import com.example.moviedataapp.movies.PhotoGridAdapter
import com.example.moviedataapp.network.Movie
import com.example.moviedataapp.network.MovieDetail
import com.example.moviedataapp.network.Topic

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MovieDetail>?) {
	val adapter = recyclerView.adapter as PhotoGridAdapter
	adapter.submitList(data)
}

//@BindingAdapter("listData")
//fun bindMoviesRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
//	val adapter = recyclerView.adapter as PhotoGridAdapter
//	adapter.submitList(data)
//}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
	imgUrl?.let {
		val imgUri = it.toUri().buildUpon().scheme("https").build()
		Glide.with(imgView.context)
			.load(imgUri)
			.apply(
				RequestOptions()
				.placeholder(R.drawable.loading_animation)
				.error(R.drawable.placeholder_image))
			.into(imgView)
	}
}

@BindingAdapter("imdbApiStatus")
fun bindStatus(statusImageView: ImageView, status: IMDbApiStatus?) {
	when (status) {
		IMDbApiStatus.LOADING -> {
			statusImageView.visibility = View.VISIBLE
			statusImageView.setImageResource(R.drawable.loading_animation)
		}
		IMDbApiStatus.ERROR -> {
			statusImageView.visibility = View.VISIBLE
			statusImageView.setImageResource(R.drawable.placeholder_image)
		}
		IMDbApiStatus.DONE -> {
			statusImageView.visibility = View.GONE
		}
	}
}