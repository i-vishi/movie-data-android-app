package com.example.moviedataapp.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedataapp.databinding.GridViewItemBinding
import com.example.moviedataapp.network.Movie
import kotlinx.android.synthetic.main.grid_view_item.view.*

//
class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Movie, PhotoGridAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieData: Movie) {
            binding.movie = movieData
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (movieData: Movie) -> Unit) {
        fun onClick(movieData: Movie) = clickListener(movieData)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Movie]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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
        holder.itemView.test_text.text = movieData.title
        holder.bind(movieData)
    }

}