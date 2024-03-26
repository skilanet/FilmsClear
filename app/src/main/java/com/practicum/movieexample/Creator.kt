package com.practicum.movieexample

import android.app.Activity
import com.practicum.movieexample.data.MoviesRepositoryImpl
import com.practicum.movieexample.data.network.RetrofitNetworkClient
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.impl.MoviesInteractorImpl
import com.practicum.movieexample.presentation.MoviesSearchController
import com.practicum.movieexample.ui.movies.MovieAdapter

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MovieAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }
}