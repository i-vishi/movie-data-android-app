package com.example.moviedataapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedataapp.home.HomeAdapter
import com.example.moviedataapp.network.Topic

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Topic>?) {
	val adapter = recyclerView.adapter as HomeAdapter
	adapter.notifyDataSetChanged()
}