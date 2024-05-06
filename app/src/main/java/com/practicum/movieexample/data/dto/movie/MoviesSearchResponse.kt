package com.practicum.movieexample.data.dto.movie

import com.practicum.movieexample.data.dto.Response

class MoviesSearchResponse(
    val results: List<MovieDto>
) : Response()