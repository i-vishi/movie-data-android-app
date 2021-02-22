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
	private val dataset: List<Topic>,
	val onClickListener: OnClickListener
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.topicTitle)
    }

    class OnClickListener(val clickListener: (apiText: String) -> Unit) {
        fun onClick(apiText: String) = clickListener(apiText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.title.text = context.resources.getString(item.title)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(context.resources.getString(item.apiString))
        }
    }

    override fun getItemCount() = dataset.size
}