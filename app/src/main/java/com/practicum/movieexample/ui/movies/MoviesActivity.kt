package com.practicum.movieexample.ui.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.practicum.movieexample.util.Creator
import com.practicum.movieexample.ui.poster.PosterActivity
import com.practicum.movieexample.R

class MoviesActivity : AppCompatActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val movieAdapter = MovieAdapter{
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }

    private var isClickAllowed = true

    private val mainHandler = Handler(Looper.getMainLooper())

    private val moviesController = Creator.provideMoviesSearchController(this, movieAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesController.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesController.onDestroy()
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}