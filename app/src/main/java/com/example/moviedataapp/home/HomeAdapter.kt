package com.example.moviedataapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedataapp.R
import com.example.moviedataapp.network.Topic

class HomeAdapter(
		private val context: Context,
		private val dataset: List<Topic>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val title: TextView = view.findViewById(R.id.topicTitle)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val adapterLayout = LayoutInflater.from(parent.context)
				.inflate(R.layout.list_item, parent, false)

		return ViewHolder(adapterLayout)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = dataset[position]
		holder.title.text = context.resources.getString(item.title)
	}

	override fun getItemCount() = dataset.size
}