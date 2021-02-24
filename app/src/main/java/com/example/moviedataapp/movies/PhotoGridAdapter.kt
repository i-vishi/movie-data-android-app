package com.example.moviedataapp.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedataapp.databinding.GridViewItemBinding
import com.example.moviedataapp.network.MovieDetail
import com.example.moviedataapp.network.MovieResult

class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<MovieResult.Movie, PhotoGridAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieData: MovieResult.Movie) {
            binding.movie = movieData
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (movieData: MovieResult.Movie) -> Unit) {
        fun onClick(movieData: MovieResult.Movie) = clickListener(movieData)
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
        return MovieViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieData = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movieData)
        }
        holder.bind(movieData)
    }

}