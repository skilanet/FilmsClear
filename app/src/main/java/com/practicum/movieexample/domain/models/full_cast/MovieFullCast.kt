package com.practicum.movieexample.domain.models.full_cast

data class MovieFullCast(
    val title: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val directors: Directors,
    val writers: Writers,
    val actors: List<Actor>,
)