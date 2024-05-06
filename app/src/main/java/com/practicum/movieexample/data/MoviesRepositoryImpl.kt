package com.practicum.movieexample.data

import com.practicum.movieexample.data.dto.details.MovieDetailsRequest
import com.practicum.movieexample.data.dto.details.MovieDetailsResponse
import com.practicum.movieexample.data.dto.full_cast.MovieFullCastRequest
import com.practicum.movieexample.data.dto.full_cast.MovieFullCastResponse
import com.practicum.movieexample.data.dto.movie.MoviesSearchRequest
import com.practicum.movieexample.data.dto.movie.MoviesSearchResponse
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.domain.models.MovieDetails
import com.practicum.movieexample.domain.models.full_cast.Actor
import com.practicum.movieexample.domain.models.full_cast.Directors
import com.practicum.movieexample.domain.models.full_cast.MovieFullCast
import com.practicum.movieexample.domain.models.full_cast.Writers
import com.practicum.movieexample.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
) : MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(MovieDetails(id, title, imDbRating, year,
                        countries, genres, directors, writers, stars, plot))
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }

    override fun getMovieFullCast(movieId: String): Resource<MovieFullCast> {
        val response = networkClient.doRequest(MovieFullCastRequest(movieId))
        return when (response.resultCode){
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as MovieFullCastResponse){
                    Resource.Success(MovieFullCast(title, fullTitle, type, year, Directors(directors.items), writers as Writers, actors as List<Actor>))
                }
            }
        }
    }
}