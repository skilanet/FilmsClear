package com.practicum.movieexample.util

import android.content.Context
import com.practicum.movieexample.data.MoviesRepositoryImpl
import com.practicum.movieexample.data.network.RetrofitNetworkClient
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.impl.MoviesInteractorImpl
import com.practicum.movieexample.presentation.movies.MoviesSearchPresenter
import com.practicum.movieexample.presentation.movies.MoviesView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchController(moviesView: MoviesView, context: Context): MoviesSearchPresenter {
        return MoviesSearchPresenter(moviesView, context)
    }
}