package com.example.moviedataapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviedataapp.movies.IMDbApiStatus
import com.example.moviedataapp.movies.PhotoGridAdapter
import com.example.moviedataapp.network.MovieResult
import com.google.android.material.progressindicator.LinearProgressIndicator

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MovieResult.Movie>?) {
	val adapter = recyclerView.adapter as PhotoGridAdapter
	adapter.submitList(data)
}

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
fun bindStatus(progressIndicator: LinearProgressIndicator, status: IMDbApiStatus?) {
	when (status) {
		IMDbApiStatus.LOADING -> {
			progressIndicator.visibility = View.VISIBLE
			progressIndicator.showAnimationBehavior
		}
		IMDbApiStatus.ERROR -> {
			progressIndicator.hideAnimationBehavior
			progressIndicator.visibility = View.GONE
		}
		IMDbApiStatus.DONE -> {
			progressIndicator.hideAnimationBehavior
			progressIndicator.visibility = View.GONE
		}
	}
}
