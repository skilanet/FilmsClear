package com.practicum.movieexample.data.network

import com.practicum.movieexample.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun findMovie(@Path ("expression") expression: String) : Call<MoviesSearchResponse>
}