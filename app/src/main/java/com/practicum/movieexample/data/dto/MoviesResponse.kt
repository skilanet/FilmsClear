package com.practicum.movieexample.data.dto

import com.practicum.movieexample.domain.models.Movie

class MoviesResponse(
    val searchType: String,
    val expression: String,
    val results: List<Movie>
) : Response()