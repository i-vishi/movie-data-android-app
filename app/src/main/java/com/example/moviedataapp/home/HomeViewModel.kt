package com.example.moviedataapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getAllOptions()
    }

    private fun getAllOptions() {
        _response.value = "Set all the options here"
    }
}