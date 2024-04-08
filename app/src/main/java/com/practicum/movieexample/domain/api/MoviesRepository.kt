package com.practicum.movieexample.domain.api

import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}