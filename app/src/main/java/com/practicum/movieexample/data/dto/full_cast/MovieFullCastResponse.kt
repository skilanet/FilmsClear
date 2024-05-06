package com.practicum.movieexample.data.dto.full_cast

import com.practicum.movieexample.data.dto.Response

data class MovieFullCastResponse(
    val title: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val directors: Directors,
    val writers: Writers,
    val actors: List<Actor>,
) : Response()