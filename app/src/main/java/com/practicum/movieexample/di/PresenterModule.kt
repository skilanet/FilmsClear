package com.practicum.movieexample.di

import com.practicum.movieexample.presentation.movies.MoviesSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel {
        MoviesSearchViewModel(get())
    }
}