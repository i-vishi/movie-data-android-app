package com.example.moviedataapp.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedataapp.databinding.GridViewItemBinding
import com.example.moviedataapp.network.MovieDetail

//
class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<MovieDetail, PhotoGridAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieData: MovieDetail) {
            binding.movie = movieData
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (movieData: MovieDetail) -> Unit) {
        fun onClick(movieData: MovieDetail) = clickListener(movieData)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Movie]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MovieDetail>() {
        override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
            return oldItem.title == newItem.title
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