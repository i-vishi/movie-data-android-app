package com.example.moviedataapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedataapp.network.IMDbApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getAllOptions()
    }

    private fun getAllOptions() {
        IMDbApi.retrofitService.getTopMovies().enqueue(object: Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                _response.value = "Success, got ${response.body()?.size} movies"
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
        _response.value = "Set all the options here"
    }
}