package com.example.moviedataapp.movies

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.GridViewItemBinding
import com.example.moviedataapp.network.MovieDetail
import com.example.moviedataapp.network.MovieResult
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate

class PhotoGridAdapter(private val context: Context) :
		ListAdapter<MovieResult.Movie, PhotoGridAdapter.MovieViewHolder>(DiffCallback) {

	lateinit var onClickListener: OnClickListener

	inner class MovieViewHolder(private var binding: GridViewItemBinding) :
			RecyclerView.ViewHolder(binding.root) {
		@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
		fun bind(movieData: MovieResult.Movie) {
			binding.movie = movieData
			val imgUrl = "https://image.tmdb.org/t/p/original" + movieData.posterPath
			val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

			Glide.with(context).asBitmap()
					.load(imgUri)
					.into(object : SimpleTarget<Bitmap>() {
						@RequiresApi(Build.VERSION_CODES.M)
						override fun onResourceReady(
								resource: Bitmap,
								transition: Transition<in Bitmap>?
						) {
							val palette = Palette.from(resource).generate()
							palette.darkVibrantSwatch?.let {
								binding.movieCard.setCardBackgroundColor(it.rgb)
								val color = context.getColor(R.color.colorOnPrimary)
								binding.movieName.setTextColor(color)
							} ?: palette.lightVibrantSwatch?.let {
								binding.movieCard.setCardBackgroundColor(it.rgb)
							}
						}
					})

			binding.movieCard.transitionName = movieData.id.toString()
			binding.movieCard.setOnClickListener {
				onClickListener.onClick(movieData, binding.movieCard, binding.movieName)
			}

			val yearString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				LocalDate.parse(movieData.releaseDate).year.toString()
			} else {
				""
			}

			binding.movieName.text = context.getString(R.string.grid_movie_title, movieData.title, yearString)

			binding.executePendingBindings()
		}
	}

	interface OnClickListener {
		fun onClick(movieData: MovieResult.Movie, cardView: MaterialCardView, textView: MaterialTextView)
	}

	/**
	 * Allows the RecyclerView to determine which items have changed when the [List] of [MovieDetail]
	 * has been updated.
	 */
	companion object DiffCallback : DiffUtil.ItemCallback<MovieResult.Movie>() {
		override fun areItemsTheSame(oldItem: MovieResult.Movie, newItem: MovieResult.Movie): Boolean {
			return oldItem === newItem
		}

		override fun areContentsTheSame(oldItem: MovieResult.Movie, newItem: MovieResult.Movie): Boolean {
			return oldItem.id == newItem.id
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
		return MovieViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
		val movieData = getItem(position)
		holder.bind(movieData)
	}

}
