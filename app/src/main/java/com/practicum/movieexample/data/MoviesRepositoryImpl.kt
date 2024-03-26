package com.practicum.movieexample.data

import com.practicum.movieexample.data.dto.MoviesSearchRequest
import com.practicum.movieexample.data.dto.MoviesSearchResponse
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.models.Movie

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {
    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description)
            }
        } else {
            emptyList()
        }
    }

}