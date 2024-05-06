package com.practicum.movieexample.di

import com.practicum.movieexample.presentation.about.AboutViewModel
import com.practicum.movieexample.presentation.movies.MoviesSearchViewModel
import com.practicum.movieexample.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel {
        MoviesSearchViewModel(get())
    }

    viewModel { (url: String) ->
        PosterViewModel(url)
    }

    viewModel { (movieId: String) ->
        AboutViewModel(movieId, get())
    }
}