package com.example.moviedataapp.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviedataapp.R
import com.example.moviedataapp.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {

    private lateinit var apiGetString: String

    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMoviesBinding.inflate(inflater)

        val arguments = MoviesFragmentArgs.fromBundle(requireArguments())

        apiGetString = arguments.selectedTopicAPI

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.moviesPhotoGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

//        viewModel.navigateToSelectedMovie.observe(this, Observer {
//            if(null != it) {
//                this.findNavController().navigate(M)
//            }
//        })

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        view.findViewById<Button>(R.id.button_second).setOnClickListener {
////            findNavController().navigate(R.id.action_moviesFragment_to_homeFragment)
////        }
//
//        view.findViewById<TextView>(R.id.textView).text = apiGetString
//    }
}