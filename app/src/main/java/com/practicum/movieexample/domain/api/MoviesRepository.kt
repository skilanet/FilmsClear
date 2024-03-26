package com.practicum.movieexample.domain.api

import com.practicum.movieexample.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}