package com.practicum.movieexample.domain.api

import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.domain.models.MovieDetails
import com.practicum.movieexample.domain.models.full_cast.MovieFullCast
import com.practicum.movieexample.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun getMovieDetails(movieId: String): Resource<MovieDetails>
    fun getMovieFullCast(movieId: String): Resource<MovieFullCast>
}