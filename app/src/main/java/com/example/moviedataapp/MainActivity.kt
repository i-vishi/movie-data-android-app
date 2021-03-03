package com.example.moviedataapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moviedataapp.detail.MovieDetailFragmentDirections

class MainActivity : AppCompatActivity() {

	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		navController = navHostFragment.navController

		setupActionBarWithNavController(navController)

	}

	override fun onSupportNavigateUp(): Boolean {
		return when(navController.currentDestination?.id) {
			R.id.movieDetailFragment -> {
				this.findNavController(R.id.nav_host_fragment).navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMoviesFragment())
				true
			}
			else -> navController.navigateUp() || super.onSupportNavigateUp()
		}
	}
}