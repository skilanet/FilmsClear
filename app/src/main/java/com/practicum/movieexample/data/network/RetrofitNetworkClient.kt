package com.practicum.movieexample.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.practicum.movieexample.data.NetworkClient
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.data.dto.details.MovieDetailsRequest
import com.practicum.movieexample.data.dto.full_cast.MovieFullCastRequest
import com.practicum.movieexample.data.dto.movie.MoviesSearchRequest

class RetrofitNetworkClient(
    private val imdbService: ImdbApi,
    private val context: Context
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if ((dto !is MoviesSearchRequest) && (dto !is MovieDetailsRequest)) {
            return Response().apply { resultCode = 400 }
        }

        val response = if (dto is MoviesSearchRequest) {
            imdbService.findMovie(dto.expression).execute()
        } else if (dto is MovieDetailsRequest) {
            imdbService.getMovieDetails(dto.movieId).execute()
        } else {
            imdbService.getMovieFullCast((dto as MovieFullCastRequest).movieId).execute()
        }
        val body = response.body()
        return body?.apply { resultCode = response.code() } ?: Response().apply {
            resultCode = response.code()
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            }
        }
        return false
    }
}