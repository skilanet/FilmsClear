package com.practicum.movieexample.data.network

import com.practicum.movieexample.data.dto.details.MovieDetailsResponse
import com.practicum.movieexample.data.dto.full_cast.MovieFullCastResponse
import com.practicum.movieexample.data.dto.movie.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun findMovie(@Path ("expression") expression: String) : Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id")
    fun getMovieFullCast(@Path("movie_id") movieId: String): Call<MovieFullCastResponse>
}