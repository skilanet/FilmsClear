package com.practicum.movieexample.presentation.movies

import com.practicum.movieexample.ui.movies.model.MoviesState

interface MoviesView {
    fun render(state: MoviesState)

    fun showToast(additionalMessage: String)
}