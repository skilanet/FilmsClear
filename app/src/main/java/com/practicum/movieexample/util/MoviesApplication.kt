package com.practicum.movieexample.util

import android.app.Application
import com.practicum.movieexample.presentation.movies.MoviesSearchPresenter

class MoviesApplication : Application() {
    var moviesSearchPresenter: MoviesSearchPresenter? = null
}